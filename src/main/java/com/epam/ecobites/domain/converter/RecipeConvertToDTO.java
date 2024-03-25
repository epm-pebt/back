package com.epam.ecobites.domain.converter;

import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.dto.RecipeDTO;
import org.springframework.core.convert.converter.Converter;

public class RecipeConvertToDTO implements Converter<Recipe, RecipeDTO> {
    @Override
    public RecipeDTO convert(Recipe source) {
        return RecipeDTO.builder()
                .id(source.getId())
                .name(source.getName())
                .dishType(source.getDishType())
                .dietCategory(source.getDietCategory())
                .time(source.getTime())
                .summary(source.getSummary())
                .image(source.getImage())
                .build();
    }
}
