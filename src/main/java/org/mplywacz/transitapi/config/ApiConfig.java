package org.mplywacz.transitapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriTemplate;

/*
Author: BeGieU
Date: 30 sie 2020
*/
@Configuration
public class ApiConfig {
    @Bean
    public ObjectMapper defaultObjectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper;
    }

    @Bean
    public UriTemplate distanceCalculatorApi() {
        return new UriTemplate(
                "http://open.mapquestapi.com/directions/v2/route?key=wNq7cvi0pFSuG50szmeFaYI6VH9c2KEP&unit=k&from={from}&to={to}");
    }
}
