package com.recipe.app.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.app.persistence.entity.ReviewEntity;
import com.recipe.app.service.ReviewService;

@RestController
@RequestMapping("/api/recipes/{idRecipe}/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewEntity>> get(@PathVariable int idRecipe) {
        return ResponseEntity.ok(this.reviewService.getAllReviewsByRecipe(idRecipe));
    }

    @Secured({"ROLE_PREMIUM", "ROLE_PROFESSIONAL"})
    @PostMapping
    public ResponseEntity<ReviewEntity> save(@PathVariable int idRecipe,
                                             @RequestBody ReviewEntity review) {
        review.setIdRecipe(idRecipe);
        return ResponseEntity.ok(this.reviewService.save(review));
    }
}
