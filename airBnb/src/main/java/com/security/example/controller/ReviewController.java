package com.security.example.controller;

import com.security.example.entity.*;
import com.security.example.repository.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final PropertyRepository propertyRepository;

    public ReviewController(ReviewRepository reviewRepository,
                            PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    //http://localhost:8080/api/v1/reviews/addReview/{propertyId}
    @PostMapping("/addReview/{propertyId}")
    public ResponseEntity<String> addReview(
            @PathVariable long propertyId,
            @RequestBody Review review,
            @AuthenticationPrincipal PropertyUser user) {

        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        Property property = optionalProperty.get();
        Review reviewByUser = reviewRepository.findReviewByUser(property, user);

        if (reviewByUser != null) {
            return new ResponseEntity<>("You have already added a review for this property", HttpStatus.BAD_REQUEST);
        }
        review.setProperty(property);
        review.setPropertyUser(user);
        reviewRepository.save(review);
        return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);

    }

    //http://localhost:8080/api/v1/reviews/userReviews
    @GetMapping("/userReviews")
    public ResponseEntity<List<Review>> getReviewsByUser(@AuthenticationPrincipal PropertyUser user) {
        List<Review> reviews = reviewRepository.findBypropertyUser(user);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }


}
