package com.example.screenmatch.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TVSeriesOmdbDTO(
        @JsonAlias("Title")
        String title,
        @JsonAlias("totalSeasons")
        Integer totalSeasons,
        @JsonAlias("imdbRating")
        String rating,
        @JsonAlias("Plot")
        String plot,
        @JsonAlias("Genre")
        String genre,
        @JsonAlias("Actors")
        String actors,
        @JsonAlias("Poster")
        String poster
) { }
