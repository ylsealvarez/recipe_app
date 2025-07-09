package com.recipe.app.persistence.repository;

import org.springframework.data.repository.ListPagingAndSortingRepository;

import com.recipe.app.persistence.entity.RecipeEntity;

public interface RecipePagSortRepository extends ListPagingAndSortingRepository<RecipeEntity, Integer> {

}
