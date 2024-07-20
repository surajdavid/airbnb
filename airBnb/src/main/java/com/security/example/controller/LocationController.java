package com.security.example.controller;

import com.security.example.dto.LocationDto;
import com.security.example.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {
    @Autowired
    private LocationService locationService;


    //http://localhost:8080/api/v1/location/newLocation
    @PostMapping("/newLocation")
    public ResponseEntity<LocationDto> addLocation(@RequestBody LocationDto locationDto) {
        LocationDto addedLocation = locationService.addLocation(locationDto);
        return new ResponseEntity<>(addedLocation, HttpStatus.CREATED);
    }


}
