package com.inpress.weatherservice.repository;

import com.inpress.weatherservice.domain.LocationWeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationWeatherRepository extends JpaRepository<LocationWeatherEntity, Long> {
}
