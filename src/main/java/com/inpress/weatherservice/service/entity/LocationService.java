package com.inpress.weatherservice.service.entity;

import com.inpress.weatherservice.domain.LocationEntity;
import com.inpress.weatherservice.dto.location.LocationDTO;
import com.inpress.weatherservice.mapper.LocationMapper;
import com.inpress.weatherservice.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository repository;
    private final LocationMapper mapper;

    public LocationDTO create(LocationDTO dto) {
        log.debug("Try to save location with dto: {}", dto);
        try {
            repository.save(mapper.toEntity(dto));
        } catch (DataIntegrityViolationException exception) {
            throw Problem.valueOf(Status.BAD_REQUEST, "Location name must be unique!");
        }
        log.info("Saved location entity: {}", dto);
        return dto;
    }

    public LocationDTO getByName(String locationName) {
        log.debug("Try to find location by name: {}", locationName);
        LocationEntity entity = repository.findByLocationName(locationName)
                .orElseThrow(() -> Problem.valueOf(Status.BAD_REQUEST, "No such location exist"));
        log.debug("Found location: {}", entity);
        return mapper.toDto(entity);
    }

    public LocationEntity getEntityByName(String locationName) {
        log.debug("Try to find location by name: {}", locationName);
        return repository.findByLocationName(locationName)
                .orElseThrow(() -> Problem.valueOf(Status.BAD_REQUEST, "No such location exist"));
    }

    public Page<LocationDTO> getAll(Pageable pageable) {
        log.debug("Try to get all locations with page: {}", pageable);
        return repository.findAll(pageable).map(mapper::toDto);
    }
}
