package com.epam.ecobites.domain.dto;

import com.epam.ecobites.domain.RecipeIngredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedRecipeDto {
    private RecipeDto recipeDto;
    List<RecipeIngredientDto> recipeIngredientDtos;
}
