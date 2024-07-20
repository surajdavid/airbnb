package com.security.example.serviceTest;

import com.security.example.dto.LocationDto;
import com.security.example.entity.Location;
import com.security.example.repository.LocationRepository;
import com.security.example.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.security.example.dto.LocationDto;
import com.security.example.repository.LocationRepository;
import com.security.example.service.LocationService;
import org.springframework.context.annotation.Bean;

@ExtendWith(MockitoExtension.class) // Using MockitoExtension to integrate Mockito with JUnit 5
public class LocationServiceTest {

    /*
         @ExtendWith(MockitoExtension.class) provides a more streamlined and convenient way to integrate Mockito with JUnit 5 tests,
      reducing manual setup code and ensuring consistent mock management.
      On the other hand, manually initializing mocks offers more flexibility and control over the mock setup process but requires
      more effort to manage mocks properly throughout the test lifecycle.

     */
    @Mock // Mock annotation for the LocationRepository dependency
    private LocationRepository locationRepository;

    @InjectMocks // InjectMocks annotation for the LocationService under test
    private LocationService locationService;

//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
        // Test annotation for the test method
    void addLocation_Success() {
        // Arrange
        LocationDto locationDto = new LocationDto(); // Creating a new LocationDto instance
        locationDto.setLocationName("Mumbai"); // Setting the location name for the LocationDto instance

        Location location = new Location(); // Creating a new Location instance
        location.setLocationName("Mumbai"); // Setting the location name for the Location instance

        when(locationRepository.save(any(Location.class))).thenReturn(location); // Mocking the save method of locationRepository to return the Location instance

        // Act
        LocationDto result = locationService.addLocation(locationDto); // Invoking the addLocation method of LocationService

        // Assert
        assertEquals("Mumbai", result.getLocationName()); // Verifying that the location name in the result matches the expected value
    }
}




