package com.example.screenmatch.service.translation.chatGPT;

import com.example.screenmatch.service.translation.ITranslation;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class SearchChatGPT implements ITranslation {
    public String getTranslation(String text) {
        OpenAiService service = new OpenAiService("you need to pay");

        CompletionRequest request = CompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .prompt("traduza para o português o texto: " + text)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var response = service.createCompletion(request);
        return response.getChoices().get(0).getText();
    }
}
