package com.recipe.app.web.controller;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.app.persistence.entity.RecipeEntity;
import com.recipe.app.service.FavoriteService;
import com.recipe.app.service.RecipeService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final FavoriteService favoriteService;

    public RecipeController(RecipeService recipeService, FavoriteService favoriteService) {
        this.recipeService = recipeService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<RecipeEntity>> getAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int elements,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        return ResponseEntity.ok(this.recipeService.getAll(page, elements, sortBy, sortDirection));
    }

    @GetMapping("/{idRecipe}")
    public ResponseEntity<RecipeEntity> get(@PathVariable int idRecipe) {
        return ResponseEntity.ok(this.recipeService.get(idRecipe));
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
    public ResponseEntity<RecipeEntity> add(@RequestBody RecipeEntity recipe) {
        if (recipe.getIdRecipe() == null || !this.recipeService.exists(recipe.getIdRecipe())) {
            return ResponseEntity.ok(this.recipeService.save(recipe));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<RecipeEntity> update(@RequestBody RecipeEntity recipe) {
        if (recipe.getIdRecipe() != null && this.recipeService.exists(recipe.getIdRecipe())) {
            return ResponseEntity.ok(this.recipeService.save(recipe));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{idRecipe}")
    public ResponseEntity<Void> delete(@PathVariable int idRecipe) {
        if (this.recipeService.exists(idRecipe)) {
            this.recipeService.delete(idRecipe);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{recipeId}/favorite")
    public ResponseEntity<Void> toggleFavorite(
            @PathVariable Integer recipeId,
            @RequestHeader("X-User-Id") String userId) {
        favoriteService.toggleFavorite(userId, recipeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/favorites")
    public ResponseEntity<Set<RecipeEntity>> getMyFavorites(
            @RequestHeader("X-User-Id") String userId) {
        Set<RecipeEntity> favs = favoriteService.getFavorites(userId);
        return ResponseEntity.ok(favs);
    }

    @GetMapping("/premium/all")
    public ResponseEntity<List<RecipeEntity>> getPremium() {
        return ResponseEntity.ok(this.recipeService.isPremium());
    }

    @GetMapping("/basic/all")
    public ResponseEntity<List<RecipeEntity>> getBasic() {
        return ResponseEntity.ok(this.recipeService.isBasic());
    }

}
