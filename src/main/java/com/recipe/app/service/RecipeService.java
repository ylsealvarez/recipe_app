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

    public List<RecipeEntity> getByType(String type) {
        return this.recipeRepository.findByTypeIgnoreCase(type);
    }

    public List<RecipeEntity> getWith(String ingredient) {
        return this.recipeRepository.findAllByStepsContainingIgnoreCase(ingredient);
    }

    public RecipeEntity save(RecipeEntity recipe){
        return this.recipeRepository.save(recipe);
    }

    public boolean exists(int idRecipe){
        return this.recipeRepository.existsById(idRecipe);
    }

    public void delete(int idRecipe){
        this.recipeRepository.deleteById(idRecipe);
    }
}
