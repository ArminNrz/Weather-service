package com.inpress.weatherservice.dto.weather;

import com.inpress.weatherservice.dto.thirdparty.openweather.InstantWeatherData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CurrentWeatherData extends BaseWeatherData {
    private InstantWeatherData weather;
}
