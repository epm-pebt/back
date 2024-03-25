package com.epam.ecobites.domain.converter;

import com.epam.ecobites.data.EcoUserRepository;
import com.epam.ecobites.data.IngredientRepository;
import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.domain.EcoUser;
import com.epam.ecobites.domain.Ingredient;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.ShoppingItem;
import com.epam.ecobites.domain.dto.ShoppingItemDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ShoppingItemConvertToEntity implements Converter<ShoppingItemDTO, ShoppingItem> {
    private RecipeRepository recipeRepository;
    private IngredientRepository ingredientRepository;
    private EcoUserRepository ecoUserRepository;

    public ShoppingItemConvertToEntity(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, EcoUserRepository ecoUserRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.ecoUserRepository = ecoUserRepository;
    }

    @Override
    public ShoppingItem convert(ShoppingItemDTO source) {
        Recipe recipe = recipeRepository.findById(source.getRecipeId())
                .orElseThrow(() -> new NotFoundException("Recipe with id " + source.getRecipeId() + " not found"));
        Ingredient ingredient = ingredientRepository.findById(source.getIngredientId())
                .orElseThrow(() -> new NotFoundException("Ingredient with id " + source.getIngredientId() + " not found"));
        EcoUser ecoUser = ecoUserRepository.findById(source.getEcoUserId())
                .orElseThrow(() -> new NotFoundException("User with id " + source.getEcoUserId() + " not found"));

        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setId(source.getId());
        shoppingItem.setRecipe(recipe);
        shoppingItem.setIngredient(ingredient);
        shoppingItem.setEcoUser(ecoUser);
        return shoppingItem;
    }
}
