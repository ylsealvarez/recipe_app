package com.recipe.app.persistence.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.recipe.app.persistence.entity.RecipeEntity;

public interface RecipeRepository extends ListCrudRepository<RecipeEntity, Integer>{
    List<RecipeEntity> findTop9ByDietIgnoreCaseOrderByRatingDesc(String diet);
    List<RecipeEntity> findByTypeIgnoreCase(String type);
    List<RecipeEntity> findAllByNameContainingIgnoreCase(String ingredients);
    List<RecipeEntity> findAllByIsPremiumTrue();
    List<RecipeEntity> findAllByIsPremiumFalse();
}
