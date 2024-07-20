package com.security.example.service;

import com.security.example.entity.Property;
import com.security.example.repository.FavouriteRepository;
import com.security.example.repository.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropertyService {


    private final PropertyRepository propertyRepository;
    private final FavouriteRepository favouriteRepository;

    public PropertyService(PropertyRepository propertyRepository, FavouriteRepository favouriteRepository) {
        this.propertyRepository = propertyRepository;
        this.favouriteRepository = favouriteRepository;
    }


    public Property addProperty(Property property) {
        return propertyRepository.save(property);
    }

    public void deletePropertyById(long id) {
        // First, delete associated Favourite records
        favouriteRepository.deleteByPropertyId(id);

        // Then, delete the Property
        propertyRepository.deleteById(id);
    }
}

