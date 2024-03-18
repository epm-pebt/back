package com.epam.ecobites.converter;

import com.epam.ecobites.domain.Ingredient;
import com.epam.ecobites.dto.IngredientDTO;

public class IngredientConverter implements EntityConverter<IngredientDTO, Ingredient> {
    @Override
    public IngredientDTO convertToDTO(Ingredient ingredient) {
        return IngredientDTO.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .build();
    }

    @Override
    public Ingredient convertToEntity(IngredientDTO ingredientDto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientDto.getId());
        ingredient.setName(ingredientDto.getName());
        return ingredient;
    }
}
