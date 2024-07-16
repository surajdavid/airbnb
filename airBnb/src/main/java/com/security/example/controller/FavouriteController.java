package com.security.example.controller;

import com.security.example.entity.Favourite;
import com.security.example.entity.PropertyUser;
import com.security.example.repository.FavouriteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/favourites")
public class FavouriteController {

    private final FavouriteRepository favouriteRepository;

    public FavouriteController(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }

    //http://localhost:8080/api/v1/favourites/addFavourite
    @PostMapping("/addFavourite")
    public ResponseEntity<Favourite> addFavourite(
            @RequestBody Favourite favourite,
            @AuthenticationPrincipal PropertyUser user) {

        favourite.setPropertyUser(user);
        Favourite savedFavourite = favouriteRepository.save(favourite);
        return new ResponseEntity<>(savedFavourite, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/v1/favourites/deleteFavourite/{id}
    @DeleteMapping("/deleteFavourite/{id}")
    public ResponseEntity<String> deleteFavouriteById(@PathVariable long id) {
        favouriteRepository.deleteByFavouriteId(id);
        return new ResponseEntity<>("Your favourite has removed", HttpStatus.OK);
    }
}
