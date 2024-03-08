package com.epam.ecobites.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "INGREDIENT_DETAIL")
public class IngredientDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private String unit;

    @OneToMany(mappedBy = "ingredientDetail")
    private List<RecipeIngredient> recipeIngredients;
}
