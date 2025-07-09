package com.recipe.app.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.app.persistence.entity.RecipeEntity;
import com.recipe.app.service.RecipeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/diet/{diet}")    
    public ResponseEntity<List<RecipeEntity>> getByDiet(@PathVariable String diet) {
        return ResponseEntity.ok(this.recipeService.getByDiet(diet));
    }
}
