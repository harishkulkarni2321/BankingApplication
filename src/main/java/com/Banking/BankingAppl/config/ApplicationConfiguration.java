package com.Banking.BankingAppl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public RestTemplate getResttemplate(){
        RestTemplate restTemplate =new RestTemplate();
        return restTemplate;
    }
}
