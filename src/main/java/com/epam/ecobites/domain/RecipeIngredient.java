package com.epam.ecobites.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "RECIPE_INGREDIENT")
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "RecipeID")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "IngredientID")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "IngredientDetailID")
    private IngredientDetail ingredientDetail;
}
