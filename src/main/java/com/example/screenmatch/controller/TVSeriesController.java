package com.example.screenmatch.controller;

import com.example.screenmatch.dto.EpisodeDTO;
import com.example.screenmatch.dto.TVSeriesDTO;
import com.example.screenmatch.service.TVSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/series")
public class TVSeriesController {
    private final TVSeriesService tvSeriesService;
    @Autowired
    public TVSeriesController(TVSeriesService tvSeriesService) {
        this.tvSeriesService = tvSeriesService;
    }

    @GetMapping()
    public List<TVSeriesDTO> getAllTVSeries(){
        return tvSeriesService.getAllTVSeries();
    }

    @GetMapping(value = "/top5")
    public List<TVSeriesDTO> getTop5TVSeries(){
        return tvSeriesService.getTop5TVSeries();
    }

    @GetMapping(value = "/lancamentos")
    public List<TVSeriesDTO> getNewReleases(){
        return tvSeriesService.getNewReleases();
    }

    @GetMapping(value = "/{id}")
    public TVSeriesDTO getById(@PathVariable Long id){
        return tvSeriesService.getById(id);
    }

    @GetMapping(value = "/{id}/temporadas/todas")
    public List<EpisodeDTO> getAllSeasonsFromTVSeriesById(@PathVariable Long id){
        return tvSeriesService.getAllSeasonsFromTVSeriesById(id);
    }

    @GetMapping(value = "/{id}/temporadas/{season}")
    public List<EpisodeDTO> getEpisodesBySeasonNumberFromTVSeriesById(@PathVariable Long id,@PathVariable Integer season){
        return tvSeriesService.getEpisodesBySeasonNumberFromTVSeriesById(id, season);
    }

    @GetMapping(value = "/{id}/temporadas/top")
    public List<EpisodeDTO> getTop5EpisodesById(@PathVariable Long id){
        return tvSeriesService.getTop5EpisodesById(id);
    }

    @GetMapping(value = "/categoria/{categoryName}")
    public List<TVSeriesDTO> getByCategory(@PathVariable String categoryName){
        return tvSeriesService.getByCategory(categoryName);
    }
}
