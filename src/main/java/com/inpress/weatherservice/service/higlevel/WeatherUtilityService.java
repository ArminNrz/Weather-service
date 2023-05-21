package com.inpress.weatherservice.service.higlevel;

import com.inpress.weatherservice.domain.enumaration.WeatherCondition;
import com.inpress.weatherservice.domain.enumaration.WindDirection;
import com.inpress.weatherservice.dto.thirdparty.openweather.InstantWeatherData;
import com.inpress.weatherservice.service.lowlevel.UtilityHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@Component
@Slf4j
@RequiredArgsConstructor
public class WeatherUtilityService {

    /**
     * This method receive list of instant weather data and group them by date of them
     * Under the hood each of this list belong to a special day and time
     * this method group them by days
     * @param data: list of instantWeatherData
     * @return group instantWeatherData by date
     */
    public Map<LocalDate, List<InstantWeatherData>> getDailyWeatherMap(List<InstantWeatherData> data) {
        return data.stream()
                .collect(Collectors.groupingBy(
                        instantWeatherData -> instantWeatherData.getLocalDateTime().toLocalDate(),
                        mapping(instantWeatherData -> instantWeatherData, toList())
                ));
    }

    /**
     * This method receive list of weather info and calculate AVG of weather parameters for this day's and return the AVG weather data
     * for numeric parameters calculate the avg
     * for max and min parameters detect min or max
     * for non-numeric parameters detect most common value
     * @param localDate: for setting the time of this avg weather data
     * @param weatherDataList: list of weather data that want to get avg of them
     * @return avg weather data
     */
    public InstantWeatherData getDailyAvgWeather(LocalDate localDate, List<InstantWeatherData> weatherDataList) {
        InstantWeatherData dailyWeatherData = new InstantWeatherData();
        dailyWeatherData.setLocalDateTime(localDate.atTime(12, 0, 0));
        Double temperatureAvg = weatherDataList.stream()
                .filter(instantWeatherData -> instantWeatherData.getTemperature() != null)
                .collect(Collectors.averagingDouble(InstantWeatherData::getTemperature));
        dailyWeatherData.setTemperature(temperatureAvg);
        Double minTemperatureAvg = weatherDataList.stream()
                .map(InstantWeatherData::getMinTemperature)
                .filter(Objects::nonNull)
                .min(Double::compare).orElse(-1D);
        dailyWeatherData.setMinTemperature(minTemperatureAvg);
        Double maxTemperatureAvg = weatherDataList.stream()
                .map(InstantWeatherData::getMaxTemperature)
                .filter(Objects::nonNull)
                .max(Double::compare).orElse(-1D);
        dailyWeatherData.setMaxTemperature(maxTemperatureAvg);
        Double atmosphericPressureAvg = weatherDataList.stream()
                .filter(instantWeatherData -> instantWeatherData.getAtmosphericPressure() != null)
                .collect(Collectors.averagingDouble(InstantWeatherData::getAtmosphericPressure));
        dailyWeatherData.setAtmosphericPressure(atmosphericPressureAvg);
        Double humidityAvg = weatherDataList.stream()
                .filter(instantWeatherData -> instantWeatherData.getHumidity() != null)
                .collect(Collectors.averagingDouble(InstantWeatherData::getHumidity));
        dailyWeatherData.setHumidity(humidityAvg);
        Double precipitationAvg = weatherDataList.stream()
                .filter(instantWeatherData -> instantWeatherData.getPrecipitation() != null)
                .collect(Collectors.averagingDouble(InstantWeatherData::getPrecipitation));
        dailyWeatherData.setPrecipitation(precipitationAvg);
        Double windSpeedAvg = weatherDataList.stream()
                .filter(instantWeatherData -> instantWeatherData.getWindSpeed() != null)
                .collect(Collectors.averagingDouble(InstantWeatherData::getWindSpeed));
        dailyWeatherData.setWindSpeed(windSpeedAvg);
        List<WindDirection> windDirections = weatherDataList.stream()
                .map(InstantWeatherData::getWindDirection)
                .filter(Objects::nonNull)
                .toList();
        WindDirection mostCommonWindDirection = UtilityHelper.mostCommon(windDirections);
        dailyWeatherData.setWindDirection(mostCommonWindDirection);
        List<WeatherCondition> weatherConditions = weatherDataList.stream()
                .map(InstantWeatherData::getCondition)
                .filter(Objects::nonNull)
                .toList();
        WeatherCondition mostCommonWeatherCondition = UtilityHelper.mostCommon(weatherConditions);
        dailyWeatherData.setCondition(mostCommonWeatherCondition);
        return dailyWeatherData;
    }
}
