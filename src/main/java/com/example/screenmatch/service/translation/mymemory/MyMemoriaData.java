package com.example.screenmatch.service.translation.mymemory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MyMemoriaData(
        TranslatedText responseData
) {
}
