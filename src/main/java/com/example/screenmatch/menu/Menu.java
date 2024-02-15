package com.example.screenmatch.menu;

import com.example.screenmatch.dto.SeasonDTO;
import com.example.screenmatch.dto.TVSeriesDTO;
import com.example.screenmatch.model.Category;
import com.example.screenmatch.model.Episode;
import com.example.screenmatch.model.TVSeries;
import com.example.screenmatch.repository.TVSeriesRepository;
import com.example.screenmatch.service.api.APIConsumer;
import com.example.screenmatch.service.parser.DataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Menu {
    private final String API_URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=7735df76";
    private final Scanner scanner = new Scanner(System.in);
    private final APIConsumer apiConsumer = new APIConsumer();
    private final DataParser dataParser = new DataParser();
    private final List<TVSeriesDTO> tvSeriesDTOList = new ArrayList<>();
    private List<TVSeries> tvSeriesList = new ArrayList<>();
    private Optional<TVSeries> tvSeriesFound;
    private final TVSeriesRepository tvSeriesRepository;

    @Autowired
    public Menu(TVSeriesRepository tvSeriesRepository) {
        this.tvSeriesRepository = tvSeriesRepository;
    }

    public void showMenu() {
        int option = -1;

        while (option != 0) {
            System.out.println("""
                    1 - (WEB) - Buscar serie por título
                    2 - (WEB) - Encontrar episodes
                    3 - (BD) - Listar series cadastradas
                    4 - (BD) - Buscar série por título
                    5 - (BD) - Buscar série por ator/atriz
                    6 - (BD) - Top 5 séries
                    7 - (BD) - Buscar série por categoria/gênero
                    8 - (BD) - Buscar série por número de temporadas e avaliação
                    9 - (BD) - Buscar episodes por trecho de nome
                    10 - (BD) - Top episódios de uma série
                    11 - (BD) - Episódios a partir de uma data
                    0 - Sair
                    """);

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    getTVSeriesWeb();
                    break;
                case 2:
                    getEpisodePerTVSeries();
                    break;
                case 3:
                    showTVSeriesList();
                    break;
                case 4:
                    getTVSeriesByTitle();
                    break;
                case 5:
                    getTVSeriesByActor();
                    break;
                case 6:
                    getTopTVSeries();
                    break;
                case 7:
                    getTVSeriesByCategory();
                    break;
                case 8:
                    getTVSeriesByTotalSeasonsAndRating();
                    break;
                case 9:
                    getEpisodesBySubstringTitle();
                    break;
                case 10:
                    topEpisodesPerTVSeries();
                    break;
                case 11:
                    getEpisodesFromDateToNow();
                    break;
                case 0:
                    System.out.println("Tachau...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }

    }

    private void getEpisodesFromDateToNow() {
        getTVSeriesByTitle();
        if(tvSeriesFound.isPresent()){
            TVSeries tvSeries = tvSeriesFound.get();
            System.out.println("Digite o ano limite de lançamento");
            int year = scanner.nextInt();
            scanner.nextLine();
            List<Episode> episodes = tvSeriesRepository.getTVSeriesEpisodesFromYear(tvSeries, year);
            episodes.forEach(e ->
                    System.out.printf("Série: %s, Temporada: %d, Episódio: %d, Ano de lançamento: %s, Título: %s \n",
                            e.getTvSeries().getTitle(),
                            e.getSeason(),
                            e.getNumber(),
                            e.getReleasedDate(),
                            e.getTitle()
                    )
            );
        }
    }

    private void topEpisodesPerTVSeries() {
        getTVSeriesByTitle();
        if(tvSeriesFound.isPresent()){
            TVSeries tvSeries = tvSeriesFound.get();
            List<Episode> episodes = tvSeriesRepository.topEpisodesPerTVSeries(tvSeries);
            episodes.forEach(e ->
                    System.out.printf("Série: %s, Temporada: %d, Episódio: %d, Avaliação: %.2f, Título: %s \n",
                            e.getTvSeries().getTitle(),
                            e.getSeason(),
                            e.getNumber(),
                            e.getRating(),
                            e.getTitle()
                    )
            );
        }
    }

    private void getEpisodesBySubstringTitle() {
        System.out.println("Digite o trecho do nome do episodio");
        String titleEpisode = scanner.nextLine().trim();

        List<Episode> episodes = tvSeriesRepository.getEpisodesBySubstringTitle(titleEpisode);
        episodes.forEach(e ->
                System.out.printf("Série: %s, Temporada: %d, Episódio: %d, Título: %s \n",
                        e.getTvSeries().getTitle(),
                        e.getSeason(),
                        e.getNumber(),
                        e.getTitle()
                )
        );
    }

    private void getTVSeriesByTotalSeasonsAndRating() {
        System.out.println("Número máximo de temporadas");
        int maxSeasons = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Mínimo de avaliação");
        double minRating = scanner.nextDouble();

        List<TVSeries> tvSeriesList = tvSeriesRepository.tvSeriesBySeasonAndRating(maxSeasons, minRating);
        tvSeriesList.forEach(s -> System.out.println("Título: " + s.getTitle() + "  avaliação: " + s.getRating()));
    }

    private void getTVSeriesByCategory() {
        System.out.println("Digite a categoria");
        String categoryName = scanner.nextLine();

        Category category = Category.fromPortugues(categoryName);

        List<TVSeries> tvSeriesList = tvSeriesRepository.findByCategory(category);
        tvSeriesList.forEach(s -> System.out.println("Título: " + s.getTitle() + "  avaliação: " + s.getRating()));
    }

    private void getTopTVSeries() {
        List<TVSeries> tvSeriesList = tvSeriesRepository.findTop5ByOrderByRatingDesc();
        tvSeriesList.forEach(s -> System.out.println("Título: " + s.getTitle() + "  avaliação: " + s.getRating()));
    }

    private void getTVSeriesByActor() {
        System.out.println("Digite o nome do ator/atriz");
        String actor = scanner.nextLine().trim();

        System.out.println("Avaliações a partir de qual valor?");
        double rating = scanner.nextDouble();
        scanner.nextLine();

        List<TVSeries> tvSeriesList = tvSeriesRepository.findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(actor, rating);
        if (!tvSeriesList.isEmpty()) {
            tvSeriesList.forEach(s -> System.out.println("Título: " + s.getTitle() + "  avaliação: " + s.getRating()));
        } else {
            System.out.println("Nome do ator/atriz inválido");
        }
    }

    private void getTVSeriesByTitle() {
        System.out.println("Digite o título da série");
        String title = scanner.nextLine().trim();

        tvSeriesFound = tvSeriesRepository.findByTitleContainingIgnoreCase(title);
        if (tvSeriesFound.isPresent()) {
            TVSeries tvSeries = tvSeriesFound.get();
            System.out.println(tvSeries);
        } else {
            System.out.println("Série não encontrada");
        }
    }

    private void showTVSeriesList() {
        tvSeriesList = tvSeriesRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(TVSeries::getCategory))
                .toList();

        tvSeriesList.forEach(System.out::println);
    }

    private void getTVSeriesWeb() {
        TVSeriesDTO dto = getTVSeriesData();
        tvSeriesDTOList.add(dto);
        tvSeriesRepository.save(new TVSeries(dto));
        System.out.println(dto);
    }

    private void getEpisodePerTVSeries() {
        showTVSeriesList();
        System.out.println("Escolha uma serie pelo nome");
        String tvseriesTitle = scanner.nextLine();

        Optional<TVSeries> tvSeriesFilter = tvSeriesRepository.findByTitleContainingIgnoreCase(tvseriesTitle);

        if (tvSeriesFilter.isPresent()) {
            TVSeries tvSeries = tvSeriesFilter.get();
            List<SeasonDTO> seasonDTOList = new ArrayList<>();

            for (int i = 1; i <= tvSeries.getTotalSeasons(); i++) {
                String url = API_URL + tvSeries.getTitle().replace(" ", "+") + "&season=" + i + API_KEY;
                String json = apiConsumer.getData(url);

                seasonDTOList.add(dataParser.getData(json, SeasonDTO.class));
            }

            seasonDTOList.forEach(System.out::println);

            List<Episode> episodeList = seasonDTOList.stream()
                    .flatMap(s -> s.episodeDTOList().stream().map(e -> new Episode(s.number(), e)))
                    .toList();

            tvSeries.setEpisodeList(episodeList);

            tvSeriesRepository.save(tvSeries);
        } else {
            System.out.println("Série inválida ou não cadastrada");
        }
    }

    private TVSeriesDTO getTVSeriesData() {
        System.out.println("Digite o nome da série");
        String tvSeriesName = scanner.nextLine().trim().replace(" ", "+");

        String json = apiConsumer.getData(API_URL + tvSeriesName + API_KEY);

        return dataParser.getData(json, TVSeriesDTO.class);
    }
}
