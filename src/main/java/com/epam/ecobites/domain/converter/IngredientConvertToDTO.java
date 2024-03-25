package com.epam.ecobites.domain.converter;

import com.epam.ecobites.domain.Ingredient;
import com.epam.ecobites.domain.dto.IngredientDTO;
import org.springframework.core.convert.converter.Converter;

public class IngredientConvertToDTO implements Converter<Ingredient, IngredientDTO> {
    @Override
    public IngredientDTO convert(Ingredient source) {
        return IngredientDTO.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}
