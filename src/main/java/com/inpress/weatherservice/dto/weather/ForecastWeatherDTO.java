package com.inpress.weatherservice.dto.weather;

import com.inpress.weatherservice.dto.thirdparty.openweather.InstantWeatherData;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ForecastWeatherDTO {
    private LocalDate localDate;
    private InstantWeatherData dailyWeather;
    private List<InstantWeatherData> hourlyWeathers = new ArrayList<>();
}
