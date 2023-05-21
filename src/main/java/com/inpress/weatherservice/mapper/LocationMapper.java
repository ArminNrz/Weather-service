package com.inpress.weatherservice.mapper;

import com.inpress.weatherservice.domain.LocationEntity;
import com.inpress.weatherservice.dto.location.LocationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(source = "name", target = "locationName")
    LocationEntity toEntity(LocationDTO dto);

    @Mapping(source = "locationName", target = "name")
    LocationDTO toDto(LocationEntity entity);
}
