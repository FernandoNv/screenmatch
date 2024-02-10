package com.example.screenmatch.menu;

import com.example.screenmatch.dto.SeasonDTO;
import com.example.screenmatch.dto.TVSeriesDTO;
import com.example.screenmatch.model.TVSeries;
import com.example.screenmatch.repository.TVSeriesRepository;
import com.example.screenmatch.service.api.APIConsumer;
import com.example.screenmatch.service.parser.DataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
@Component
public class Menu {
    private final String API_URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=7735df76";
    private final Scanner scanner = new Scanner(System.in);
    private final APIConsumer apiConsumer = new APIConsumer();
    private final DataParser dataParser = new DataParser();
    private final List<TVSeriesDTO> tvSeriesDTOList = new ArrayList<>();
    private final TVSeriesRepository tvSeriesRepository;
    @Autowired
    public Menu(TVSeriesRepository tvSeriesRepository) {
        this.tvSeriesRepository = tvSeriesRepository;
    }

    public void showMenu() {
        int option = -1;

        while (option != 0){
            System.out.println("""
                1 - Find TV Series
                2 - Find Episodes
                3 - Show List TV Series
                0 - Exit
                """);

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option){
                case 1:
                    getTVSeries();
                    break;
                case 2:
                    getEpisodePerTVSeries();
                    break;
                case 3:
                    showTVSeriesList();
                    break;
                case 0:
                    System.out.println("Bye Bye...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }

    }

    private void showTVSeriesList() {
        List<TVSeries> tvSeriesList = tvSeriesRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(TVSeries::getCategory))
                .toList();

        tvSeriesList.forEach(System.out::println);
    }

    private void getTVSeries(){
        TVSeriesDTO dto = getTVSeriesData();
        tvSeriesDTOList.add(dto);
        tvSeriesRepository.save(new TVSeries(dto));
        System.out.println(dto);
    }

    private void getEpisodePerTVSeries() {
        TVSeriesDTO tvSeriesDTO = getTVSeriesData();
        List<SeasonDTO> seasonDTOList = new ArrayList<>();

        for (int i = 1; i <= tvSeriesDTO.totalSeasons(); i++) {
            String url = API_URL + tvSeriesDTO.title().replace(" ", "+")  + "&season="+ i + API_KEY;
            String json = apiConsumer.getData(url);

            seasonDTOList.add(dataParser.getData(json, SeasonDTO.class));
        }

        seasonDTOList.forEach(System.out::println);
    }

    private TVSeriesDTO getTVSeriesData(){
        System.out.println("Type the TV Series name");
        String tvSeriesName = scanner.nextLine().trim().replace(" ", "+");

        String json = apiConsumer.getData(API_URL+tvSeriesName+API_KEY);

        return dataParser.getData(json, TVSeriesDTO.class);
    }
}
