package com.example.screenmatch.service;

import com.example.screenmatch.dto.EpisodeDTO;
import com.example.screenmatch.dto.TVSeriesDTO;
import com.example.screenmatch.model.Category;
import com.example.screenmatch.model.Episode;
import com.example.screenmatch.model.TVSeries;
import com.example.screenmatch.repository.TVSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TVSeriesService {
    private final TVSeriesRepository tvSeriesRepository;

    @Autowired
    public TVSeriesService(TVSeriesRepository tvSeriesRepository) {
        this.tvSeriesRepository = tvSeriesRepository;
    }

    private TVSeriesDTO toDTO(TVSeries tvSeries) {
        return new TVSeriesDTO(tvSeries.getId(),
                tvSeries.getTitle(),
                tvSeries.getTotalSeasons(),
                tvSeries.getRating(),
                tvSeries.getPlot(),
                tvSeries.getCategory(),
                tvSeries.getActors(),
                tvSeries.getPoster());
    }

    private List<TVSeriesDTO> toDTOList(List<TVSeries> tvSeriesList) {
        return tvSeriesList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TVSeriesDTO> getAllTVSeries() {
        return this.toDTOList(tvSeriesRepository.findAll());
    }

    public List<TVSeriesDTO> getTop5TVSeries() {
        return this.toDTOList(tvSeriesRepository.findTop5ByOrderByRatingDesc());
    }

    public List<TVSeriesDTO> getNewReleases() {
        return this.toDTOList(tvSeriesRepository.findNewReleases());
    }


    public TVSeriesDTO getById(Long id) {
        Optional<TVSeries> tvSeries = tvSeriesRepository.findById(id);
        return tvSeries.map(this::toDTO).orElse(null);
    }

    public List<EpisodeDTO> getAllSeasonsFromTVSeriesById(Long id) {
        Optional<TVSeries> tvSeriesFound = tvSeriesRepository.findById(id);
        return tvSeriesFound.map(tvSeries -> tvSeries
                .getEpisodeList().stream()
                .map(e -> new EpisodeDTO(e.getTitle(), e.getNumber(), e.getSeason()))
                .collect(Collectors.toList())
        ).orElse(null);
    }

    public List<EpisodeDTO> getEpisodesBySeasonNumberFromTVSeriesById(Long id, Integer season) {
        List<Episode> episodeList = tvSeriesRepository.getEpisodesBySeasonNumberFromTVSeriesById(id, season);

        return episodeList.stream()
                .map(e -> new EpisodeDTO(e.getTitle(), e.getNumber(), e.getSeason()))
                .collect(Collectors.toList());
    }

    public List<TVSeriesDTO> getByCategory(String categoryName) {
        Category category = Category.fromPortugues(categoryName);
        List<TVSeries> tvSeriesList = tvSeriesRepository.findByCategory(category);

        return tvSeriesList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EpisodeDTO> getTop5EpisodesById(Long id) {
        List<Episode> episodeList = tvSeriesRepository.findTop5EpisodesById(id);

        return episodeList.stream()
                .map(e -> new EpisodeDTO(e.getTitle(), e.getNumber(), e.getSeason()))
                .collect(Collectors.toList());
    }
}
