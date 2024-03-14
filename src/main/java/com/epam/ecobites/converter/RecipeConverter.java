package com.epam.ecobites.converter;

import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.dto.RecipeDTO;

public class RecipeConverter implements EntityConverter<RecipeDTO, Recipe> {
    @Override
    public RecipeDTO toDTO(Recipe recipe) {
        return RecipeDTO.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .dishType(recipe.getDishType())
                .dietCategory(recipe.getDietCategory())
                .time(recipe.getTime())
                .summary(recipe.getSummary())
                .image(recipe.getImage())
                .build();
    }

    @Override
    public Recipe fromDTO(RecipeDTO recipeDto) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());
        recipe.setName(recipeDto.getName());
        recipe.setDishType(recipeDto.getDishType());
        recipe.setDietCategory(recipeDto.getDietCategory());
        recipe.setTime(recipeDto.getTime());
        recipe.setSummary(recipeDto.getSummary());
        recipe.setImage(recipeDto.getImage());
        return recipe;
    }
}
