package com.security.example.repository;


import com.security.example.entity.Favourite;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    // Method to delete Favourite records by property_id for delete property
    @Transactional
    @Modifying
    @Query("DELETE FROM Favourite f WHERE f.property.id = :propertyId")
    void deleteByPropertyId(@Param("propertyId") Long propertyId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Favourite f WHERE f.id =:favouriteId")
    void deleteByFavouriteId(@Param("favouriteId") Long id);


}
