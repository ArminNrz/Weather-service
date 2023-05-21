package com.inpress.weatherservice.service.thirdparty;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.Coordinate;
import com.github.prominence.openweathermap.api.model.forecast.Forecast;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import com.inpress.weatherservice.dto.location.LocationDTO;
import com.inpress.weatherservice.dto.thirdparty.openweather.InstantWeatherData;
import com.inpress.weatherservice.mapper.WeatherMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OpenWeatherService {

    private final OpenWeatherMapClient client;
    private final WeatherMapper weatherMapper;

    /////////////////////////////////////////////////
    //      FORECAST
    ////////////////////////////////////////////////

    public List<InstantWeatherData> getWeatherForecast(LocationDTO locationDTO) {
        double latitude = locationDTO.getLatitude();
        double longitude = locationDTO.getLongitude();
        List<InstantWeatherData> weatherDataList;
        try {
            log.debug("Try to send request to get forecast weather from open-weather service, with lat: {}, lon: {}", latitude, longitude);
            weatherDataList = get5DaysForecastWeatherWith3HourSteps(latitude, longitude);
            log.info("Received forecast weather info from open-weather service, and response: {}", weatherDataList);
        } catch (Exception exception) {
            throw Problem.valueOf(Status.REQUEST_TIMEOUT, "Sorry there is an error in communicate with open-weather service. please try again!");
        }
        return weatherDataList;
    }

    private List<InstantWeatherData> get5DaysForecastWeatherWith3HourSteps(double latitude, double longitude) {
        Forecast forecast = client.forecast5Day3HourStep()
                .byCoordinate(Coordinate.of(latitude, longitude))
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJava();
        return forecast.getWeatherForecasts().stream()
                .map(weatherMapper::fromInstantForecast)
                .toList();
    }

    /////////////////////////////////////////////////
    //      CURRENT
    ////////////////////////////////////////////////

    public InstantWeatherData getCurrentWeatherData(LocationDTO locationDTO) {
        double latitude = locationDTO.getLatitude();
        double longitude = locationDTO.getLongitude();
        Weather weather;
        try {
            log.debug("Try to send request to get current weather from open-weather service, with lat: {}, lon: {}", latitude, longitude);
            weather = this.getCurrentWeatherData(latitude, longitude);
            log.info("Received current weather info from open-weather service, and response: {}", weather);
        } catch (Exception exception) {
            throw Problem.valueOf(Status.REQUEST_TIMEOUT, "Sorry there is an error in communicate with open-weather service. please try again!");
        }
        return weatherMapper.fromWeather(weather);
    }

    private Weather getCurrentWeatherData(double lat, double lon) {
        return client.currentWeather()
                .single()
                .byCoordinate(Coordinate.of(lat, lon))
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJava();
    }
}
