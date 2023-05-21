package com.inpress.weatherservice.service.entity;

import com.inpress.weatherservice.domain.LocationWeatherEntity;
import com.inpress.weatherservice.dto.weather.CurrentWeatherData;
import com.inpress.weatherservice.mapper.LocationWeatherMapper;
import com.inpress.weatherservice.repository.LocationWeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationWeatherService {

    private final LocationWeatherRepository repository;
    private final LocationWeatherMapper mapper;

    public void persist(CurrentWeatherData currentWeatherData) {
        log.debug("Try to save current weather data with: {}", currentWeatherData);
        LocationWeatherEntity entity = mapper.fromCurrentWeather(currentWeatherData.getWeather(), currentWeatherData.getLocationDTO().getId());
        entity.setDateTime(LocalDateTime.now());
        repository.save(entity);
        log.info("Saved current weather: {}, for location: {}", entity, currentWeatherData.getLocationDTO());
    }
}
