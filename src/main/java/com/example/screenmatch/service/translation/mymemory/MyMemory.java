package com.example.screenmatch.service.translation.mymemory;

import com.example.screenmatch.service.api.APIConsumer;
import com.example.screenmatch.service.parser.DataParser;
import com.example.screenmatch.service.translation.ITranslation;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MyMemory implements ITranslation {
    private APIConsumer apiConsumer = new APIConsumer();
    private DataParser dataParser = new DataParser();
    @Override
    public String getTranslation(String text) {
        String textFormatted = URLEncoder.encode(text, StandardCharsets.UTF_8);
        String langPair = URLEncoder.encode("en|pt-Br", StandardCharsets.UTF_8);

        String url = "https://api.mymemory.translated.net/get?q="+textFormatted+"&langpair="+langPair;
        String json = apiConsumer.getData(url);
        MyMemoriaData myMemoriaData = dataParser.getData(json, MyMemoriaData.class);

        return myMemoriaData.responseData().translatedText();
    }
}
