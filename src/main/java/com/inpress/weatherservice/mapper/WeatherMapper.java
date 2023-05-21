package com.inpress.weatherservice.mapper;

import com.github.prominence.openweathermap.api.model.forecast.WeatherForecast;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import com.inpress.weatherservice.dto.thirdparty.openweather.InstantWeatherData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface WeatherMapper {

    @Mapping(target = "localDateTime", source = "forecast.forecastTime")
    @Mapping(target = "condition", expression = "java(com.inpress.weatherservice.service.lowlevel.WeatherHelper.fromOWMCode(forecast.getWeatherState().getId()))")
    @Mapping(target = "temperature", source = "temperature.value")
    @Mapping(target = "minTemperature", source = "temperature.minTemperature")
    @Mapping(target = "maxTemperature", source = "temperature.maxTemperature")
    @Mapping(target = "atmosphericPressure", source = "atmosphericPressure.value")
    @Mapping(target = "humidity", source = "humidity.value")
    @Mapping(target = "precipitation", source = "forecast", qualifiedByName = "detectForecastPrecipitation")
    @Mapping(target = "windSpeed", source = "wind.speed")
    @Mapping(target = "windDirection", expression = "java(com.inpress.weatherservice.service.lowlevel.WeatherHelper.fromDirection(forecast.getWind().getDegrees()))")
    InstantWeatherData fromInstantForecast(WeatherForecast forecast);

    @Named("detectForecastPrecipitation")
    default double detectForecastPrecipitation(WeatherForecast weatherForecast) {
        if (weatherForecast.getRain() == null) {
            return 0;
        }
        else {
            return weatherForecast.getRain().getThreeHourLevel();
        }
    }

    @Mapping(target = "localDateTime", source = "weather.calculationTime")
    @Mapping(target = "condition", expression = "java(com.inpress.weatherservice.service.lowlevel.WeatherHelper.fromOWMCode(weather.getWeatherState().getId()))")
    @Mapping(target = "temperature", source = "temperature.value")
    @Mapping(target = "minTemperature", source = "temperature.minTemperature")
    @Mapping(target = "maxTemperature", source = "temperature.maxTemperature")
    @Mapping(target = "atmosphericPressure", source = "atmosphericPressure.value")
    @Mapping(target = "humidity", source = "humidity.value")
    @Mapping(target = "precipitation", source = "weather", qualifiedByName = "detectPrecipitation")
    @Mapping(target = "windSpeed", source = "wind.speed")
    @Mapping(target = "windDirection", expression = "java(com.inpress.weatherservice.service.lowlevel.WeatherHelper.fromDirection(weather.getWind().getDegrees()))")
    InstantWeatherData fromWeather(Weather weather);

    @Named("detectPrecipitation")
    default double detectPrecipitation(Weather weather) {
        if (weather.getRain() == null) {
            return 0;
        }
        else {
            return weather.getRain().getThreeHourLevel();
        }
    }
}
