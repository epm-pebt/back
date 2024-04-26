package com.epam.ecobites.service;

import com.epam.ecobites.data.EcoUserRepository;
import com.epam.ecobites.data.RecipeIngredientRepository;
import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.domain.EcoUser;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.RecipeIngredient;
import com.epam.ecobites.domain.ShoppingItem;
import com.epam.ecobites.domain.dto.DetailedRecipeDto;
import com.epam.ecobites.domain.mapper.DetailedRecipeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DetailedRecipeServiceImpl implements DetailedRecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final DetailedRecipeMapper detailedRecipeMapper;

    @Autowired
    public DetailedRecipeServiceImpl(
            RecipeRepository recipeRepository,
            RecipeIngredientRepository recipeIngredientRepository,
            DetailedRecipeMapper detailedRecipeMapper,
            EcoUserRepository ecoUserRepository){
        this.recipeRepository = recipeRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.detailedRecipeMapper = detailedRecipeMapper;
    }

    @Override
    public DetailedRecipeDto getRecipeDetails(String recipeName) {
        Recipe recipe = recipeRepository.findByName(recipeName);
        List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findByRecipeId(recipe.getId());
        return detailedRecipeMapper.toDetailedRecipe(recipe, recipeIngredientList);
    }
}
