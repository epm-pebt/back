package com.epam.ecobites.converter;

import com.epam.ecobites.domain.Ingredient;
import com.epam.ecobites.dto.IngredientDTO;
import org.springframework.core.convert.converter.Converter;

public class IngredientConvertToEntity implements Converter<IngredientDTO, Ingredient> {
    @Override
    public Ingredient convert(IngredientDTO source) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setName(source.getName());
        return ingredient;
    }
}
