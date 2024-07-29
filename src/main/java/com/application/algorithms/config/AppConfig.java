package com.application.algorithms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public double defaultDoubleValue() {
        return 0.0; // Return any default value you prefer
    }
}