package com.epam.ecobites.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "RECIPE_INGREDIENT")
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredient {
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
    @JoinColumn(name = "IngredientDetailId")
    private IngredientDetail ingredientDetail;

    @Override
    public String toString() {
        return "RecipeIngredient{}";
    }
}
