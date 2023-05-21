package com.inpress.weatherservice.controller;

import com.inpress.weatherservice.constant.Constant;
import com.inpress.weatherservice.dto.weather.CurrentWeatherData;
import com.inpress.weatherservice.dto.weather.ForecastWeatherData;
import com.inpress.weatherservice.service.higlevel.CurrentWeatherService;
import com.inpress.weatherservice.service.higlevel.ForecastWeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(Constant.BASE_URL + Constant.VERSION + "/weather")
public class WeatherController {

    private final ForecastWeatherService forecastWeatherService;
    private final CurrentWeatherService currentWeatherService;

    @GetMapping("/forecast/{locationName}")
    public ResponseEntity<ForecastWeatherData> getForecastWeather(@PathVariable("locationName") String locationName) {
        log.info("REST request to get forecast weather for location name: {}", locationName);
        return ResponseEntity.ok(forecastWeatherService.get(locationName));
    }

    @GetMapping("/current/{locationName}")
    public ResponseEntity<CurrentWeatherData> getCurrentWeather(@PathVariable("locationName") String locationName) {
        log.info("REST request to get current weather for location name: {}", locationName);
        return ResponseEntity.ok(currentWeatherService.get(locationName));
    }

    @GetMapping("/history/{locationName}")
    public ResponseEntity<List<CurrentWeatherData>> getWeatherHistory(@PathVariable("locationName") String locationName) {
        log.info("REST request to get weather histories, for location name: {}", locationName);
        return ResponseEntity.ok(currentWeatherService.getWeatherHistory(locationName));
    }
}
