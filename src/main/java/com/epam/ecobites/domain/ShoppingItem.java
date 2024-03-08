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
    @JoinColumn(name = "RecipeID")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "IngredientID")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private EcoUser ecoUser;

    @OneToMany(mappedBy = "shoppingItem")
    private List<ShoppingItemDetail> shoppingItemDetails;
}

