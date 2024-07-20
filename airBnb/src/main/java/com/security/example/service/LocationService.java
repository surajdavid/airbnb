package com.security.example.service;

import com.security.example.dto.LocationDto;
import com.security.example.entity.Location;
import com.security.example.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
    public LocationDto addLocation(LocationDto locationDto) {
        Location location=new Location();
        location.setLocationName(locationDto.getLocationName());

        locationRepository.save(location);
        LocationDto dto = new LocationDto();
        dto.setId(location.getId());
        dto.setLocationName(location.getLocationName());
        return dto;
    }


}
