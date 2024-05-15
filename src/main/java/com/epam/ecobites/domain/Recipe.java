package com.epam.ecobites.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "RECIPE")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private DishType dishType;

    @Enumerated(EnumType.STRING)
    private DietCategory dietCategory;

    private int time;
    private String summary;
    private String image;

    @OneToMany(mappedBy = "recipe")
    private List<Review> reviews;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeIngredient> recipeIngredients;

    @OneToMany(mappedBy = "recipe")
    private List<ShoppingItem> shoppingItems;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeStep> recipeSteps;
}
