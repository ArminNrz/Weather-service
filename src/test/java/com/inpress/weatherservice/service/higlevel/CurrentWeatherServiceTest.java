package com.inpress.weatherservice.service.higlevel;

import com.inpress.weatherservice.domain.LocationEntity;
import com.inpress.weatherservice.domain.enumaration.WeatherCondition;
import com.inpress.weatherservice.domain.enumaration.WindDirection;
import com.inpress.weatherservice.dto.location.LocationDTO;
import com.inpress.weatherservice.dto.thirdparty.openweather.InstantWeatherData;
import com.inpress.weatherservice.dto.weather.CurrentWeatherData;
import com.inpress.weatherservice.mapper.LocationWeatherMapper;
import com.inpress.weatherservice.service.entity.LocationService;
import com.inpress.weatherservice.service.entity.LocationWeatherService;
import com.inpress.weatherservice.service.thirdparty.OpenWeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class CurrentWeatherServiceTest {

    @Autowired
    private CurrentWeatherService currentWeatherService;
    @Autowired
    private LocationWeatherMapper locationWeatherMapper;
    @MockBean
    private LocationService locationService;
    @MockBean
    private OpenWeatherService openWeatherService;
    @MockBean
    private LocationWeatherService locationWeatherService;

    private InstantWeatherData instantWeatherData;
    private long locationId;
    private String locationName;

    @BeforeEach
    void setUp() {
        this.instantWeatherData = new InstantWeatherData();
        instantWeatherData.setLocalDateTime(LocalDateTime.now());
        instantWeatherData.setCondition(WeatherCondition.DRIZZLE);
        instantWeatherData.setTemperature(12D);
        instantWeatherData.setMinTemperature(10D);
        instantWeatherData.setMaxTemperature(15D);
        instantWeatherData.setAtmosphericPressure(12.5);
        instantWeatherData.setHumidity(67.0);
        instantWeatherData.setPrecipitation(0.0);
        instantWeatherData.setWindSpeed(120D);
        instantWeatherData.setWindDirection(WindDirection.EAST);

        locationId = 1;
        locationName = "Test";
    }

    @Test
    @DisplayName("success scenario get current weather")
    void getCurrentTest() {

        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(locationId);
        locationDTO.setLatitude(24.05415);
        locationDTO.setLongitude(15.054151);
        locationDTO.setName(locationName);
        Mockito.when(locationService.getByName(locationName))
                .thenReturn(locationDTO);

        Mockito.when(openWeatherService.getCurrentWeatherData(locationDTO))
                .thenReturn(instantWeatherData);

        CurrentWeatherData actualWeatherData = currentWeatherService.get(locationName);

        Assertions.assertNotNull(actualWeatherData);
        Assertions.assertEquals(instantWeatherData, actualWeatherData.getWeather());
        Assertions.assertEquals(locationDTO, actualWeatherData.getLocationDTO());
    }

    @Test
    void getHistoryTest() {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setId(locationId);
        locationEntity.setLocationName(locationName);
        locationEntity.setLatitude(125.555);
        locationEntity.setLongitude(15.515);
        locationEntity.getLocationWeathers().add(locationWeatherMapper.fromCurrentWeather(instantWeatherData, locationId));

        Mockito.when(locationService.getEntityByName(locationName))
                .thenReturn(locationEntity);

        List<CurrentWeatherData> actualResponse = currentWeatherService.getWeatherHistory(locationName);

        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(1, actualResponse.size());
    }
}