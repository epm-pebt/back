package com.epam.ecobites.domain.dto;

import com.epam.ecobites.domain.Ingredient;
import com.epam.ecobites.domain.IngredientDetail;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredientDto {
    private IngredientDto ingredientDto;
    private IngredientDetailDto ingredientDetailDto;
}
