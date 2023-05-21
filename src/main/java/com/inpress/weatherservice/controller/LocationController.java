package com.inpress.weatherservice.controller;

import com.inpress.weatherservice.constant.Constant;
import com.inpress.weatherservice.dto.location.LocationDTO;
import com.inpress.weatherservice.service.entity.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(Constant.BASE_URL + Constant.VERSION + "/location")
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationDTO> create(@Valid @RequestBody LocationDTO dto) {
        log.info("REST request to create location with: {}", dto);
        return ResponseEntity.created(URI.create("/location")).body(locationService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<LocationDTO>> getAll(Pageable pageable) {
        log.info("REST request to get all locations");
        Page<LocationDTO> page = locationService.getAll(pageable);
        return ResponseEntity.ok(page.getContent());
    }

    @GetMapping("/{locationName}")
    public ResponseEntity<LocationDTO> getByName(@PathVariable("locationName") String locationName) {
        log.info("REST request to get location with name: {}", locationName);
        return ResponseEntity.ok(locationService.getByName(locationName));
    }
}
