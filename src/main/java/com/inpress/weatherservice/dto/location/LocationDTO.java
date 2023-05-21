package com.inpress.weatherservice.dto.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LocationDTO {
    @NotNull(message = "latitude must not be empty")
    private Double latitude;
    @NotNull(message = "longitude must be empty")
    private Double longitude;
    @NotEmpty(message = "location name must be empty")
    private String name;

    @JsonIgnore
    private Long id;
}
