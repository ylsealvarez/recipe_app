package com.recipe.app.web.controller;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<RecipeEntity> get(@PathVariable int idRecipe, @AuthenticationPrincipal UserDetails user) {
        RecipeEntity recipe = this.recipeService.get(idRecipe);

        boolean isPremiumRecipe = Boolean.TRUE.equals(recipe.getIsPremium());

        boolean hasPremiumOrPro = user != null &&
                user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .anyMatch(auth -> auth.equals("ROLE_PREMIUM") ||
                                auth.equals("ROLE_PROFESSIONAL"));

        if (isPremiumRecipe && !hasPremiumOrPro) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Acceso PREMIUM o PROFESSIONAL requerido");
        }

        return ResponseEntity.ok(recipe);
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
            Principal principal) {
        String username = principal.getName();  
        favoriteService.toggleFavorite(username, recipeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<RecipeEntity>> getMyFavorites(Principal principal) {
        String username = principal.getName();                   
        List<RecipeEntity> favs = this.favoriteService.getFavorites(username);
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
