package com.inpress.weatherservice.configuration;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenWeatherConfiguration {

    @Value("${inpress.openweathermap.api-key}")
    private String apiKey;

    @Bean
    public OpenWeatherMapClient getOpenWeatherMapClient() {
        OpenWeatherMapClient client = new OpenWeatherMapClient(apiKey);
        client.setReadTimeout(1000);
        client.setConnectionTimeout(1000);
        return client;
    }
}
