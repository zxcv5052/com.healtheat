package com.healthteat.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SlackConfig {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
