package com.inpress.weatherservice.dto.weather;

import com.inpress.weatherservice.dto.location.LocationDTO;
import lombok.Data;

@Data
public abstract class BaseWeatherData {
    private LocationDTO locationDTO;
}
