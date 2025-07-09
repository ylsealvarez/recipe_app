package com.recipe.app.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchase_item")
@IdClass(PurchaseItemId.class)
@Getter
@Setter
@NoArgsConstructor
public class PurchaseItemEntity {
    @Id
    @Column(name = "id_purchase", nullable = false)
    private Integer idPurchase;

    @Id
    @Column(name = "id_item", nullable = false)
    private Integer idItem;

    @Column(name = "recipe_id", nullable = false)
    private Integer idRecipe;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "id_purchase", referencedColumnName = "id_purchase", insertable = false, updatable = false)
    private PurchaseEntity purchase;

    @OneToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id", insertable = false, updatable = false)
    private RecipeEntity recipe;
}
