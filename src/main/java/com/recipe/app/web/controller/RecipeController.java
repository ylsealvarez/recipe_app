package com.recipe.app.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.app.persistence.entity.RecipeEntity;
import com.recipe.app.service.RecipeService;

import org.springframework.web.bind.annotation.DeleteMapping;
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

    @GetMapping("/type/{type}")    
    public ResponseEntity<List<RecipeEntity>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(this.recipeService.getByType(type));
    }

    @GetMapping("/contains/{ingredient}")    
    public ResponseEntity<List<RecipeEntity>> getWith(@PathVariable String ingredient) {
        return ResponseEntity.ok(this.recipeService.getWith(ingredient));
    }

    @PostMapping
    public ResponseEntity<RecipeEntity> add(@RequestBody RecipeEntity recipe){
        if(recipe.getIdRecipe() == null || !this.recipeService.exists(recipe.getIdRecipe())){
            return ResponseEntity.ok(this.recipeService.save(recipe));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<RecipeEntity> update(@RequestBody RecipeEntity recipe){
        if(recipe.getIdRecipe() != null && this.recipeService.exists(recipe.getIdRecipe())){
            return ResponseEntity.ok(this.recipeService.save(recipe));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{idRecipe}")
    public ResponseEntity<Void> delete(@PathVariable int idRecipe){
        if(this.recipeService.exists(idRecipe)){
            this.recipeService.delete(idRecipe);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
