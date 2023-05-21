package com.inpress.weatherservice.service.higlevel;

import com.inpress.weatherservice.domain.enumaration.WeatherCondition;
import com.inpress.weatherservice.domain.enumaration.WindDirection;
import com.inpress.weatherservice.dto.location.LocationDTO;
import com.inpress.weatherservice.dto.thirdparty.openweather.InstantWeatherData;
import com.inpress.weatherservice.dto.weather.ForecastWeatherDTO;
import com.inpress.weatherservice.dto.weather.ForecastWeatherData;
import com.inpress.weatherservice.service.entity.LocationService;
import com.inpress.weatherservice.service.lowlevel.UtilityHelper;
import com.inpress.weatherservice.service.thirdparty.OpenWeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ForecastWeatherService {

    private final LocationService locationService;
    private final OpenWeatherService openWeatherService;
    private final WeatherUtilityService weatherUtilityService;

    /**
     * This method get forecast weather of this location for 5 days every 3 hours
     * fetch the location data and detect lat and lon of this location from DB
     * send them to open-weather service and retrieve forecast weather data list
     *
     * @param locationName: location that you want
     * @return forecast weather for 5 days each 3 hour
     */
    public ForecastWeatherData get(String locationName) {
        log.debug("Try to retrieve weather forecast for location name: {}", locationName);
        LocationDTO locationDTO = locationService.getByName(locationName);
        List<InstantWeatherData> instantWeatherDataList = openWeatherService.getWeatherForecast(locationDTO);
        ForecastWeatherData forecastWeatherData = this.getEachDay(instantWeatherDataList);
        forecastWeatherData.setDate(LocalDate.now());
        forecastWeatherData.setLocationDTO(locationDTO);
        return forecastWeatherData;
    }

    /**
     * This method receive list of weather data for different days, group them by date of them
     * for each day calculate the avg weather data (because open-weather does not send us the avg of day weather data)
     * set each day list on forecast weather data
     *
     * @param data: list of different days weather data
     * @return forecast weather data
     */
    private ForecastWeatherData getEachDay(List<InstantWeatherData> data) {
        Map<LocalDate, List<InstantWeatherData>> dailyWeatherMap = weatherUtilityService.getDailyWeatherMap(data);

        ForecastWeatherData result = new ForecastWeatherData();
        List<ForecastWeatherDTO> forecastWeatherDTOList = new ArrayList<>();
        dailyWeatherMap.forEach((localDate, weatherDataList) -> {
            ForecastWeatherDTO forecastWeatherDTO = new ForecastWeatherDTO();
            InstantWeatherData dailyWeather = weatherUtilityService.getDailyAvgWeather(localDate, weatherDataList);
            forecastWeatherDTO.setLocalDate(localDate);
            forecastWeatherDTO.setDailyWeather(dailyWeather);
            forecastWeatherDTO.setHourlyWeathers(weatherDataList);
            forecastWeatherDTOList.add(forecastWeatherDTO);
        });
        result.setWeathers(forecastWeatherDTOList);
        return result;
    }
}
