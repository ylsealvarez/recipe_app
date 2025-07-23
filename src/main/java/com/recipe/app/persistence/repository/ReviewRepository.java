package com.recipe.app.persistence.repository;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.recipe.app.persistence.entity.ReviewEntity;

public interface ReviewRepository extends ListCrudRepository<ReviewEntity, Integer>{
    List<ReviewEntity> findAllByIdRecipeOrderByCreatedAt(Integer idRecipe);
}
