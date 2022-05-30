package com.example.springboot99;

import com.example.springboot99.config.NotionConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(NotionConfigProperties.class)
public class Springboot99Application {

    public static void main(String[] args) {
        SpringApplication.run(Springboot99Application.class, args);
    }
}
