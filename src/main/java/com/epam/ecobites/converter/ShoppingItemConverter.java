package com.epam.ecobites.converter;

import com.epam.ecobites.data.EcoUserRepository;
import com.epam.ecobites.data.IngredientRepository;
import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.domain.EcoUser;
import com.epam.ecobites.domain.Ingredient;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.ShoppingItem;
import com.epam.ecobites.dto.ShoppingItemDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ShoppingItemConverter implements EntityConverter<ShoppingItemDTO, ShoppingItem> {
    private RecipeRepository recipeRepository;
    private IngredientRepository ingredientRepository;
    private EcoUserRepository ecoUserRepository;

    public ShoppingItemConverter(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, EcoUserRepository ecoUserRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.ecoUserRepository = ecoUserRepository;
    }

    @Override
    public ShoppingItemDTO convertToDTO(ShoppingItem shoppingItem) {
        return ShoppingItemDTO.builder()
                .id(shoppingItem.getId())
                .recipeId(shoppingItem.getRecipe().getId())
                .ingredientId(shoppingItem.getIngredient().getId())
                .ecoUserId(shoppingItem.getEcoUser().getId())
                .build();
    }

    @Override
    public ShoppingItem convertToEntity(ShoppingItemDTO shoppingItemDto) {
        Recipe recipe = recipeRepository.findById(shoppingItemDto.getRecipeId())
                .orElseThrow(() -> new NotFoundException("Recipe with id " + shoppingItemDto.getRecipeId() + " not found"));
        Ingredient ingredient = ingredientRepository.findById(shoppingItemDto.getIngredientId())
                .orElseThrow(() -> new NotFoundException("Ingredient with id " + shoppingItemDto.getIngredientId() + " not found"));
        EcoUser ecoUser = ecoUserRepository.findById(shoppingItemDto.getEcoUserId())
                .orElseThrow(() -> new NotFoundException("User with id " + shoppingItemDto.getEcoUserId() + " not found"));

        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setId(shoppingItemDto.getId());
        shoppingItem.setRecipe(recipe);
        shoppingItem.setIngredient(ingredient);
        shoppingItem.setEcoUser(ecoUser);
        return shoppingItem;
    }
}
