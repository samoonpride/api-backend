package com.samoonpride.backend.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@AllArgsConstructor
@Configuration
public class WebClientConfig {
    @Getter
    private static WebClient webClient;
    private final WebClient.Builder webClientBuilder;

    @Bean
    public WebClient setWebClient() {
        webClient = webClientBuilder
                .defaultHeader("Authorization", "Bearer " + LineConfig.getChannelToken())
                .build();
        return webClient;
    }
}
