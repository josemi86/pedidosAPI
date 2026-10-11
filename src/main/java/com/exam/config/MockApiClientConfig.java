package com.exam.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class MockApiClientConfig {

    @Value("${mockapi.base.url}")
    private String BASE_URL;

    @Bean
    public RestClient todoRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl(BASE_URL)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}