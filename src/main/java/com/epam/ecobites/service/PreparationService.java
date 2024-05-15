package com.epam.ecobites.service;

import com.epam.ecobites.domain.RecipeStep;
import com.epam.ecobites.domain.dto.RecipeStepDto;

import java.util.List;

public interface PreparationService {
    List<RecipeStepDto> getRecipeSteps(String recipeName);
}
