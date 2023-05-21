package com.inpress.weatherservice.dto.thirdparty.openweather;

import com.inpress.weatherservice.domain.enumaration.WeatherCondition;
import com.inpress.weatherservice.domain.enumaration.WindDirection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InstantWeatherData {
    private LocalDateTime localDateTime;
    private WeatherCondition condition;
    private Double temperature;
    private Double minTemperature;
    private Double maxTemperature;
    private Double atmosphericPressure;
    private Double humidity;
    private Double precipitation;
    private Double windSpeed;
    private WindDirection windDirection;
}
