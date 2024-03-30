package com.knu.KnowcKKnowcK.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class ChatGptConfig {
    @Value("${openai.gpt.secret.key}")
    private String key;
    private final WebClientConfig webClientConfig;
    private final String url = "https://api.openai.com/v1/chat/completions";

    public WebClient gptClient(){
        return webClientConfig.webClient().mutate()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.AUTHORIZATION,"Bearer "+key)
                .build();
    }
}
