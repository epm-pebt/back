package com.epam.ecobites.converter;

import com.epam.ecobites.domain.RecipeStep;
import com.epam.ecobites.dto.RecipeStepDTO;
import org.springframework.core.convert.converter.Converter;

public class RecipeStepConvertToDTO implements Converter<RecipeStep, RecipeStepDTO> {
    @Override
    public RecipeStepDTO convert(RecipeStep source) {
        return RecipeStepDTO.builder()
                .id(source.getId())
                .number(source.getNumber())
                .title(source.getTitle())
                .description(source.getDescription())
                .image(source.getImage())
                .recipeId(source.getRecipe().getId())
                .build();
    }
}
