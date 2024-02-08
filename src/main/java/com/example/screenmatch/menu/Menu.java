package com.example.screenmatch.menu;

import com.example.screenmatch.dto.EpisodeDTO;
import com.example.screenmatch.dto.SeasonDTO;
import com.example.screenmatch.dto.TVSeriesDTO;
import com.example.screenmatch.model.Episode;
import com.example.screenmatch.service.APIConsumer;
import com.example.screenmatch.service.DataParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private final String API_URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=7735df76";
    private final APIConsumer apiConsumer = new APIConsumer();
    private final DataParser dataParser = new DataParser();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void showMenu() {
        System.out.println("Type the tv series name");
        String tvSeriesName = scanner.nextLine().trim().replace(" ", "+");

        String json = apiConsumer.getData(API_URL+tvSeriesName+API_KEY);
        TVSeriesDTO tvSeriesDTO = dataParser.getData(json, TVSeriesDTO.class);

        System.out.println(tvSeriesDTO);

        List<SeasonDTO> seasonDTOList = new ArrayList<>();
        for (int i = 1; i <= tvSeriesDTO.totalSeasons(); i++) {
            String url = API_URL + tvSeriesName + "&season="+ i + API_KEY;
            json = apiConsumer.getData(url);

            seasonDTOList.add(dataParser.getData(json, SeasonDTO.class));
        }

        List<EpisodeDTO> episodeDTOList = seasonDTOList.stream()
                .flatMap(s -> s.episodeDTOList().stream())
                .collect(Collectors.toList());
                //.toList(); immutable list

        System.out.println("Top 5 episodes");
        episodeDTOList.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeDTO::rating).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episode> episodes = seasonDTOList.stream()
                .flatMap(s -> s.episodeDTOList().stream().map(e -> new Episode(e, s.number())))
                .collect(Collectors.toList());

        episodes.forEach(System.out::println);
        Map<Integer, Double> ratingPerSeason = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeason,
                        Collectors.averagingDouble(Episode::getRating)));

        System.out.println(ratingPerSeason);

        DoubleSummaryStatistics est = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getRating));

        System.out.printf("""
                Average: %.2f \n
                The best episode: %.2f \n
                The worst episode: %.2f \n
                Numer of episodes: %d
                """, est.getAverage(), est.getMax(), est.getMin(), est.getCount());

//        System.out.println("Type the title of your favourite episode ");
//        String substringTitle = scanner.nextLine().trim();
//
//        Optional<Episode> episode = episodes.stream()
//                .filter(e -> e.getTitle().toUpperCase().contains(substringTitle.toUpperCase()))
//                .findFirst();
//
//        if(episode.isEmpty()){
//            System.out.println("Episode not found");
//        }else{
//            System.out.println("Episode " + episode.get());
//        }


//        System.out.println("Type the search year");
//        int year = scanner.nextInt();
//        scanner.nextLine();
//
//        LocalDate searchDate = LocalDate.of(year, 1, 1);
//        episodes.stream()
//                .filter(e -> e.getReleasedDate() != null && e.getReleasedDate().isAfter(searchDate))
//                .forEach(e -> System.out.printf(
//                        """
//                                Season: %d\s
//                                Episode: %d\s
//                                Released: %s%n""",
//                        e.getSeason(),
//                        e.getNumber(),
//                        e.getReleasedDate().format(dateTimeFormatter)
//                ));

    }
}
