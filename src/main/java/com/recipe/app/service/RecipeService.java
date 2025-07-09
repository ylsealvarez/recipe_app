package com.recipe.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.recipe.app.persistence.entity.RecipeEntity;

import com.recipe.app.persistence.repository.RecipeRepository;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<RecipeEntity> getByDiet(String diet) {
        return this.recipeRepository.findByDietIgnoreCase(diet);
    }
}
