package com.recipe.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.recipe.app.persistence.entity.RecipeEntity;
import com.recipe.app.persistence.repository.RecipePagSortRepository;
import com.recipe.app.persistence.repository.RecipeRepository;

@Service
public class RecipeService {

    private final RecipePagSortRepository recipePagSortRepository;
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository, RecipePagSortRepository recipePagSortRepository) {
        this.recipeRepository = recipeRepository;
        this.recipePagSortRepository = recipePagSortRepository;
    }

    public Page<RecipeEntity> getAll(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return this.recipePagSortRepository.findAll(pageRequest);
    }

    public List<RecipeEntity> getByDiet(String diet) {
        return this.recipeRepository.findTop9ByDietIgnoreCaseOrderByRatingDesc(diet);
    }

    public RecipeEntity get(int idRecipe){
        return this.recipeRepository.findById(idRecipe).orElse(null);
    }

    public List<RecipeEntity> getByType(String type) {
        return this.recipeRepository.findByTypeIgnoreCase(type);
    }

    public List<RecipeEntity> getWith(String ingredient) {
        return this.recipeRepository.findAllByNameContainingIgnoreCase(ingredient);
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

    public List<RecipeEntity> isPremium() {
        return this.recipeRepository.findAllByIsPremiumTrue();
    }

    public List<RecipeEntity> isBasic() {
        return this.recipeRepository.findAllByIsPremiumFalse();
    }
}
