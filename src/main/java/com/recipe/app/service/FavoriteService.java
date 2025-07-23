package com.recipe.app.service;

import java.util.Collections;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recipe.app.persistence.entity.RecipeEntity;
import com.recipe.app.persistence.entity.UserEntity;
import com.recipe.app.persistence.repository.RecipeRepository;
import com.recipe.app.persistence.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FavoriteService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public FavoriteService(UserRepository userRepository, RecipeRepository recipeRepository) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    public void toggleFavorite(String userId, Integer recipeId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        Set<RecipeEntity> favs = user.getFavorites();
        if (favs.contains(recipe)) {
            favs.remove(recipe);
        } else {
            favs.add(recipe);
        }
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Set<RecipeEntity> getFavorites(String userId) {
        return userRepository.findById(userId)
                .map(UserEntity::getFavorites)
                .orElse(Collections.emptySet());
    }

}
