package com.inpress.weatherservice.domain;

import com.inpress.weatherservice.domain.enumaration.WeatherCondition;
import com.inpress.weatherservice.domain.enumaration.WindDirection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "location_weather")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class LocationWeatherEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "time")
    private LocalDateTime dateTime;

    @Column(name = "weather_condition")
    @Enumerated(EnumType.STRING)
    private WeatherCondition condition;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "min_temperature")
    private Double minTemperature;

    @Column(name = "max_temperature")
    private Double maxTemperature;

    @Column(name = "atmospheric_pressure")
    private Double atmosphericPressure;

    @Column(name = "humidity")
    private Double humidity;

    @Column(name = "precipitation")
    private Double precipitation;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @Column(name = "wind_direction")
    @Enumerated(EnumType.STRING)
    private WindDirection windDirection;

    @CreationTimestamp
    @Column(name = "created")
    private ZonedDateTime created;

    @ManyToOne
    @JoinColumn(name = "location_id")
    @ToString.Exclude
    private LocationEntity locationEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LocationWeatherEntity entity = (LocationWeatherEntity) o;
        return getId() != null && Objects.equals(getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
