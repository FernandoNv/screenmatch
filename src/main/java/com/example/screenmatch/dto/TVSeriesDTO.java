package com.example.screenmatch.dto;

import com.example.screenmatch.model.Category;

public record TVSeriesDTO(
        Long id,
        String title,
        Integer totalSeasons,
        Double rating,
        String plot,
        Category category,
        String actors,
        String poster
) { }
