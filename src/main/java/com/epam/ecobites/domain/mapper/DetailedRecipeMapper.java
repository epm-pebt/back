package com.epam.ecobites.domain.mapper;

import com.epam.ecobites.domain.Ingredient;
import com.epam.ecobites.domain.IngredientDetail;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.RecipeIngredient;
import com.epam.ecobites.domain.dto.DetailedRecipeDto;
import com.epam.ecobites.domain.dto.IngredientDetailDto;
import com.epam.ecobites.domain.dto.IngredientDto;
import com.epam.ecobites.domain.dto.RecipeIngredientDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetailedRecipeMapper {

    IngredientDto toIngredientDto(Ingredient ingredient);
    IngredientDetailDto toIngredientDetailDto(IngredientDetail ingredientDetail);
    @Mapping(source = "ingredient", target = "ingredientDto")
    @Mapping(source = "ingredientDetail", target = "ingredientDetailDto")
    RecipeIngredientDto toRecipeIngredientDto(RecipeIngredient recipeIngredient);

    List<RecipeIngredientDto> toRecipeIngredientDtos(List<RecipeIngredient> recipeIngredients);
}
