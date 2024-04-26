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
    //private final EcoUserRepository ecoUserRepository;

    @Autowired
    public DetailedRecipeServiceImpl(
            RecipeRepository recipeRepository,
            RecipeIngredientRepository recipeIngredientRepository,
            DetailedRecipeMapper detailedRecipeMapper,
            EcoUserRepository ecoUserRepository){
        this.recipeRepository = recipeRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.detailedRecipeMapper = detailedRecipeMapper;
        //this.ecoUserRepository = ecoUserRepository;
    }

    @Override
    public DetailedRecipeDto getRecipeDetails(String recipeName) {
        Recipe recipe = recipeRepository.findByName(recipeName);
        List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findByRecipeId(recipe.getId());
        return detailedRecipeMapper.toDetailedRecipe(recipe, recipeIngredientList);
    }

   /*@Override
    public List<ShoppingItem> addToGrocery(String recipeName, String ecoUsername) {
        Recipe recipe = recipeRepository.findByName(recipeName);
        List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findByRecipeId(recipe.getId());
        EcoUser ecoUser = this.ecoUserRepository.findByUsername(ecoUsername).orElseThrow(()-> new NoSuchElementException("User not found with the name "+ecoUsername));

        return List.of();
    }

    private List<ShoppingItem> createShoppingItems(List<RecipeIngredient> recipeIngredients, EcoUser ecoUser){
        List<ShoppingItem> shoppingItems = new ArrayList<>();
        recipeIngredients.forEach(ri ->{
            ShoppingItem shoppingItem = new ShoppingItem();
            shoppingItem.setRecipe(ri.getRecipe());
            shoppingItem.setIngredient(ri.getIngredient());
            shoppingItem.setEcoUser(ecoUser);
            shoppingItem.setShoppingItemDetail();
            shoppingItems.add(shoppingItem);
        });
    }*/
}
