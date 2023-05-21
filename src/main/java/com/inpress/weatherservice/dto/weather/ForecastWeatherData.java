package com.inpress.weatherservice.dto.weather;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ForecastWeatherData extends BaseWeatherData {
    private LocalDate date;
    private List<ForecastWeatherDTO> weathers;
}
