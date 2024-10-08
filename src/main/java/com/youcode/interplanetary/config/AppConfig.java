package com.youcode.interplanetary.config;

import com.youcode.interplanetary.GlobalConverters.FormatConverter;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public FormatConverter formatConverter() {
        return new FormatConverter();
    }
}
