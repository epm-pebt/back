package com.epam.ecobites.converter;

import com.epam.ecobites.domain.Ingredient;
import com.epam.ecobites.dto.IngredientDTO;

public class IngredientConverter implements EntityConverter<IngredientDTO, Ingredient> {
    @Override
    public IngredientDTO toDTO(Ingredient ingredient) {
        return IngredientDTO.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .build();
    }

    @Override
    public Ingredient fromDTO(IngredientDTO ingredientDto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientDto.getId());
        ingredient.setName(ingredientDto.getName());
        return ingredient;
    }
}
