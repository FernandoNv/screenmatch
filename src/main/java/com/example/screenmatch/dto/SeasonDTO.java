package com.example.screenmatch.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonDTO(
        @JsonAlias("Season")
        Integer number,
        @JsonAlias("Episodes")
        List<EpisodeDTO> episodeDTOList
) { }
