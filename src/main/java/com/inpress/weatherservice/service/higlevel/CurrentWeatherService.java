package com.inpress.weatherservice.service.higlevel;

import com.inpress.weatherservice.domain.LocationEntity;
import com.inpress.weatherservice.domain.LocationWeatherEntity;
import com.inpress.weatherservice.dto.location.LocationDTO;
import com.inpress.weatherservice.dto.thirdparty.openweather.InstantWeatherData;
import com.inpress.weatherservice.dto.weather.CurrentWeatherData;
import com.inpress.weatherservice.mapper.LocationMapper;
import com.inpress.weatherservice.mapper.LocationWeatherMapper;
import com.inpress.weatherservice.service.entity.LocationService;
import com.inpress.weatherservice.service.entity.LocationWeatherService;
import com.inpress.weatherservice.service.thirdparty.OpenWeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrentWeatherService {
    
    private final OpenWeatherService openWeatherService;
    private final LocationService locationService;
    private final LocationWeatherService locationWeatherService;
    private final LocationWeatherMapper locationWeatherMapper;
    private final LocationMapper locationMapper;
    private final WeatherUtilityService weatherUtilityService;

    /**
     * This method try to retrieve current weather for location and persist this info in DB
     *
     * @param locationName: the location name that you want to retrieve current weather for it
     * @return current weather info of that location
     */
    public CurrentWeatherData get(String locationName) {
        log.debug("Try to get current weather of location: {}", locationName);
        CurrentWeatherData result = new CurrentWeatherData();
        LocationDTO locationDTO = locationService.getByName(locationName);
        InstantWeatherData weather = openWeatherService.getCurrentWeatherData(locationDTO);
        result.setWeather(weather);
        result.setLocationDTO(locationDTO);
        locationWeatherService.persist(result);
        return result;
    }

    /**
     * This method try to get AVG of previous location weather that fetched before for each day
     * 1- fetch location info by name of location and fetch list of weather of that location that retried before from open-weather service
     * 2- group this weather list by date of them (each day you may retrieve weather many times then peer each day you have a list of weather infos)
     * 3- calculate the weather parameters avg for each day and set them on list of weather data for each day
     *
     * @param locationName: the name of location that you want
     * @return list of weather avg for passed days
     */
    public List<CurrentWeatherData> getWeatherHistory(String locationName) {
        List<CurrentWeatherData> result = new ArrayList<>();
        LocationEntity locationEntity = locationService.getEntityByName(locationName);
        Set<LocationWeatherEntity> locationWeatherEntities = locationEntity.getLocationWeathers();
        Map<LocalDate, List<InstantWeatherData>> dailyWeatherMap = getDailyWeatherMap(locationWeatherEntities);
        dailyWeatherMap.forEach((dayDate, dailyWeatherList) -> setDailyWeather(result, locationEntity, dayDate, dailyWeatherList));
        return result;
    }

    private void setDailyWeather(List<CurrentWeatherData> result, LocationEntity locationEntity, LocalDate dayDate, List<InstantWeatherData> dailyWeatherList) {
        InstantWeatherData instantWeatherData = weatherUtilityService.getDailyAvgWeather(dayDate, dailyWeatherList);
        CurrentWeatherData currentWeatherData = new CurrentWeatherData();
        currentWeatherData.setLocationDTO(locationMapper.toDto(locationEntity));
        currentWeatherData.setWeather(instantWeatherData);
        result.add(currentWeatherData);
    }

    private Map<LocalDate, List<InstantWeatherData>> getDailyWeatherMap(Set<LocationWeatherEntity> locationWeatherEntities) {
        List<InstantWeatherData> instantWeatherDataList = locationWeatherEntities.stream()
                .map(locationWeatherMapper::toInstantWeather)
                .toList();
        return weatherUtilityService.getDailyWeatherMap(instantWeatherDataList);
    }
}
