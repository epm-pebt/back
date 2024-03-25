package com.epam.ecobites.domain.converter;

import com.epam.ecobites.domain.ShoppingItem;
import com.epam.ecobites.domain.dto.ShoppingItemDTO;
import org.springframework.core.convert.converter.Converter;

public class ShoppingItemConvertToDTO implements Converter<ShoppingItem, ShoppingItemDTO> {
    @Override
    public ShoppingItemDTO convert(ShoppingItem source) {
        return ShoppingItemDTO.builder()
                .id(source.getId())
                .recipeId(source.getRecipe().getId())
                .ingredientId(source.getIngredient().getId())
                .ecoUserId(source.getEcoUser().getId())
                .build();
    }
}
