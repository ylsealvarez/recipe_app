package com.recipe.app.persistence.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.recipe.app.persistence.entity.RecipeEntity;

public interface RecipeRepository extends ListCrudRepository<RecipeEntity, Integer>{
    List<RecipeEntity> findByDietIgnoreCase(String diet);
}
