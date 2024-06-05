package com.epam.ecobites.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "SHOPPING_ITEM")
public class ShoppingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "RecipeId")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "IngredientId")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private EcoUser ecoUser;

    @ManyToOne
    @JoinColumn(name = "ShoppingItemDetailId")
    private ShoppingItemDetail shoppingItemDetail;
}

