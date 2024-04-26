package com.epam.ecobites.service;

import com.epam.ecobites.data.EcoUserRepository;
import com.epam.ecobites.data.RecipeIngredientRepository;
import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.domain.EcoUser;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.RecipeIngredient;
import com.epam.ecobites.domain.ShoppingItem;
import com.epam.ecobites.domain.dto.DetailedRecipeDto;
import com.epam.ecobites.domain.dto.RecipeDto;
import com.epam.ecobites.domain.dto.RecipeIngredientDto;
import com.epam.ecobites.domain.mapper.DetailedRecipeMapper;
import com.epam.ecobites.domain.mapper.RecipeMapper;
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
    private final RecipeMapper recipeMapper;

    @Autowired
    public DetailedRecipeServiceImpl(
            RecipeRepository recipeRepository,
            RecipeIngredientRepository recipeIngredientRepository,
            DetailedRecipeMapper detailedRecipeMapper,
            RecipeMapper recipeMapper
            ){
        this.recipeRepository = recipeRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.detailedRecipeMapper = detailedRecipeMapper;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public DetailedRecipeDto getRecipeDetails(String recipeName) {
        Recipe recipe = recipeRepository.findByName(recipeName);
        List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findByRecipeId(recipe.getId());
        return createRecipeIngredientDtos(recipeIngredientList, recipe);
    }

    private DetailedRecipeDto createRecipeIngredientDtos(List<RecipeIngredient> recipeIngredients, Recipe recipe){
        DetailedRecipeDto detailedRecipeDto = new DetailedRecipeDto();
        List<RecipeIngredientDto> recipeIngredientDtos = detailedRecipeMapper.toRecipeIngredientDtos(recipeIngredients);
        RecipeDto recipeDto = recipeMapper.toRecipeDto(recipe);
        detailedRecipeDto.setRecipeIngredientDtos(recipeIngredientDtos);
        detailedRecipeDto.setRecipeDto(recipeDto);
        return detailedRecipeDto;
    }
}
