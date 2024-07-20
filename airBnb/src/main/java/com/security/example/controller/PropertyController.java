package com.security.example.controller;

import com.security.example.entity.Property;
import com.security.example.repository.PropertyRepository;
import com.security.example.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    private final PropertyRepository propertyRepository;
    private final PropertyService propertyService;

    public PropertyController(PropertyRepository propertyRepository, PropertyService propertyService) {
        this.propertyRepository = propertyRepository;
        this.propertyService = propertyService;
    }

    //http://localhost:8080/api/v1/properties/addProperty
    @PostMapping("/addProperty")
    public ResponseEntity<Property> addProperty(@RequestBody Property property) {
        Property addedProperty = propertyService.addProperty(property);
        return new ResponseEntity<>(addedProperty, HttpStatus.CREATED);
    }


    //http://localhost:8080/api/v1/properties/{locationName}
    @GetMapping("/{locationName}")
    public ResponseEntity<List<Property>> findProperty(@PathVariable String locationName) {
        List<Property> properties = propertyRepository.findPropertyByLocation(locationName);
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    //http://localhost:8080/api/v1/properties/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePropertyById(@PathVariable long id) {
        propertyService.deletePropertyById(id);
        return new ResponseEntity<>("Property deleted successfully with id : " + id, HttpStatus.OK);
    }





}
