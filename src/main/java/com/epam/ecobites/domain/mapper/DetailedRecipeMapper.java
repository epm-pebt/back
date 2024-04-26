package com.epam.ecobites.domain.mapper;

import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.RecipeIngredient;
import com.epam.ecobites.domain.dto.DetailedRecipeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetailedRecipeMapper {
    DetailedRecipeDto toDetailedRecipe(Recipe recipe, List<RecipeIngredient> recipeIngredientList);
}
