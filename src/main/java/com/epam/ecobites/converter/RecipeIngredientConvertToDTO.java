package com.epam.ecobites.converter;

import com.epam.ecobites.data.IngredientDetailRepository;
import com.epam.ecobites.data.IngredientRepository;
import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.domain.RecipeIngredient;
import com.epam.ecobites.dto.RecipeIngredientDTO;
import org.springframework.core.convert.converter.Converter;

public class RecipeIngredientConvertToDTO implements Converter<RecipeIngredient, RecipeIngredientDTO> {
    @Override
    public RecipeIngredientDTO convert(RecipeIngredient source) {
        return RecipeIngredientDTO.builder()
                .id(source.getId())
                .recipeId(source.getRecipe().getId())
                .ingredientId(source.getIngredient().getId())
                .ingredientDetailId(source.getIngredientDetail().getId())
                .build();
    }
}
