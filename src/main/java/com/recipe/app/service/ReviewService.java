package com.recipe.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.recipe.app.persistence.entity.ReviewEntity;
import com.recipe.app.persistence.repository.ReviewRepository;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService (ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewEntity> getAllReviewsByRecipe(int idRecipe) {
        return this.reviewRepository.findAllByIdRecipeOrderByCreatedAt(idRecipe);
    }

    public ReviewEntity save(ReviewEntity review){
        return this.reviewRepository.save(review);
    }

}
