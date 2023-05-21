package com.inpress.weatherservice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "location", indexes = {
        @Index(name = "location_name_idx", columnList = "location_name")
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class LocationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "location_name", nullable = false, unique = true)
    private String locationName;

    @CreationTimestamp
    @Column(name = "created", nullable = false)
    private ZonedDateTime created;

    @OneToMany(mappedBy = "locationEntity", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<LocationWeatherEntity> locationWeathers = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LocationEntity that = (LocationEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
