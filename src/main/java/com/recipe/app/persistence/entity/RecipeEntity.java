package com.recipe.app.persistence.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "recipes")
@Getter
@Setter
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id", nullable = false)
    private Integer idRecipe;

    @Column(name = "recipe_name", nullable = false)
    private String name;

    @Column(name = "prep_time")
    private String prepTime;

    @Column(name = "cook_time")
    private String cookTime;

    @Column(name = "total_time")
    private String totalTime;

    private Integer servings;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String ingredients;

    @Column(name = "directions", columnDefinition = "TEXT", nullable = false)
    private String steps;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(nullable = false, length = 20)
    private String diet;

    private Double rating;

    @Column(name = "is_premium", columnDefinition = "boolean default false", nullable = false)
    private Boolean isPremium;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @ManyToMany(mappedBy = "favorites")
    @JsonIgnore
    private Set<UserEntity> favoredBy = new HashSet<>();
    
}

