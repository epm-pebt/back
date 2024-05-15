package com.epam.ecobites.service;

import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.data.RecipeStepRepository;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.RecipeStep;
import com.epam.ecobites.domain.dto.RecipeStepDto;
import com.epam.ecobites.domain.mapper.RecipeStepMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class PreparationServiceImpl implements PreparationService{

    RecipeRepository recipeRepository;
    RecipeStepRepository recipeStepRepository;
    RecipeStepMapper recipeStepMapper;

    @Autowired
    public PreparationServiceImpl(
            RecipeRepository recipeRepository,
            RecipeStepRepository recipeStepRepository,
            RecipeStepMapper recipeStepMapper
            ){
        this.recipeRepository = recipeRepository;
        this.recipeStepRepository = recipeStepRepository;
        this.recipeStepMapper = recipeStepMapper;
    }

    @Override
    public List<RecipeStepDto> getRecipeSteps(String recipeName) {
        Recipe recipe = recipeRepository.findByName(recipeName).orElseThrow(
                ()-> new NoSuchElementException("There is no recipe named "+recipeName)
        );
        List<RecipeStep> recipeSteps = recipeStepRepository.findByRecipeId(recipe.getId());
        return recipeSteps.stream().map(recipeStepMapper::toRecipeStepDto).toList();
    }
}
