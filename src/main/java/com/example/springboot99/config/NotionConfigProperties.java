package com.example.springboot99.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("notion")
public record NotionConfigProperties(
        String openWeatherApiKey,
        String databaseUrl,
        String databaseUsername,
        String databasePassword) {

}
