package com.inpress.weatherservice.mapper;

import com.inpress.weatherservice.domain.LocationWeatherEntity;
import com.inpress.weatherservice.dto.thirdparty.openweather.InstantWeatherData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface LocationWeatherMapper {

    @Mapping(target = "locationEntity.id", source = "locationId")
    @Mapping(target = "dateTime", source = "weatherData.localDateTime")
    LocationWeatherEntity fromCurrentWeather(InstantWeatherData weatherData, Long locationId);

    @Mapping(target = "localDateTime", source = "dateTime")
    InstantWeatherData toInstantWeather(LocationWeatherEntity weatherEntity);
}
