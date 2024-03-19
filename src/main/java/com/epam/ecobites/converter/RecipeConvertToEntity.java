package com.epam.ecobites.converter;

import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.dto.RecipeDTO;
import org.springframework.core.convert.converter.Converter;

public class RecipeConvertToEntity implements Converter<RecipeDTO, Recipe> {
    @Override
    public Recipe convert(RecipeDTO source) {
        Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setName(source.getName());
        recipe.setDishType(source.getDishType());
        recipe.setDietCategory(source.getDietCategory());
        recipe.setTime(source.getTime());
        recipe.setSummary(source.getSummary());
        recipe.setImage(source.getImage());
        return recipe;
    }
}
