package com.epam.ecobites.service;

import com.epam.ecobites.data.RecipeIngredientRepository;
import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.domain.Ingredient;
import com.epam.ecobites.domain.IngredientDetail;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.RecipeIngredient;
import com.epam.ecobites.domain.dto.*;
import com.epam.ecobites.domain.mapper.DetailedRecipeMapper;
import com.epam.ecobites.domain.mapper.RecipeMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DetailedRecipeServiceImplTest {
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private RecipeIngredientRepository recipeIngredientRepository;
    @Mock
    private DetailedRecipeMapper detailedRecipeMapper;
    @Mock
    private RecipeMapper recipeMapper;
    @InjectMocks
    private DetailedRecipeServiceImpl detailedRecipeService;

    private static final String IMAGE_URL = "Test image";
    private static final Long RECIPE_INGREDIENT_ID = 1L;
    private static final String RECIPE_NAME = "Recipe name";
    private static final Long RECIPE_ID = 1L;
    private static final int RECIPE_TIME = 30;
    private static final String INGREDIENT_NAME = "Ingredient name";
    private static final Long INGREDIENT_ID = 1L;
    private static final String INGREDIENT_DETAIL_UNIT = "Ingredient name";
    private static final Long INGREDIENT_DETAIL_ID = 1L;
    private static final int INGREDIENT_DETAIL_QUANTITY= 2;
    private static final String DISH_TYPE = "LUNCH";
    private static final String DIET_CATEGORY = "LUNCH";


    @DisplayName("Test getDetailedRecipe by recipe name")
    @Test
    void testGetDetailedRecipe(){
        Recipe recipe =
                createRecipe(RECIPE_ID, RECIPE_NAME, RECIPE_TIME);
        Ingredient ingredient =
                createIngredient(INGREDIENT_ID, INGREDIENT_NAME,new ArrayList<>());
        IngredientDetail ingredientDetail =
                createIngredientDetail(INGREDIENT_DETAIL_ID, INGREDIENT_DETAIL_QUANTITY, INGREDIENT_DETAIL_UNIT, new ArrayList<>());

        List<RecipeIngredient> recipeIngredients = createRecipeIngredient(RECIPE_INGREDIENT_ID, ingredient, recipe, ingredientDetail);
        ingredientDetail.setRecipeIngredients(recipeIngredients);
        ingredient.setRecipeIngredients(recipeIngredients);
        recipe.setRecipeIngredients(recipeIngredients);

        RecipeDto recipeDto = new RecipeDto(RECIPE_ID, RECIPE_NAME, RECIPE_TIME, IMAGE_URL, DISH_TYPE, DIET_CATEGORY);
        RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto(new IngredientDto(INGREDIENT_NAME), new IngredientDetailDto(INGREDIENT_DETAIL_QUANTITY, INGREDIENT_DETAIL_UNIT));
        List<RecipeIngredientDto> recipeIngredientDtos = new ArrayList<>();
        recipeIngredientDtos.add(recipeIngredientDto);
        DetailedRecipeDto detailedRecipeDto = new DetailedRecipeDto(recipeDto, recipeIngredientDtos);


        when(recipeRepository.findByName(any(String.class)))
                .thenReturn(Optional.of(recipe));
        when(recipeIngredientRepository.findByRecipeId(any(Long.class)))
                .thenReturn(recipeIngredients);
        when(recipeMapper.toRecipeDto(any(Recipe.class)))
                .thenReturn(recipeDto);
        when(detailedRecipeMapper.toRecipeIngredientDtos(any(ArrayList.class)))
                .thenReturn(recipeIngredientDtos);
        assertEquals(detailedRecipeDto, detailedRecipeService.getRecipeDetails(RECIPE_NAME));
    }


    private Recipe createRecipe(Long id, String name, int time) {
        Recipe recipe = new Recipe();
        recipe.setId(id);
        recipe.setName(name);
        recipe.setTime(time);
        recipe.setImage("Test Image");
        return recipe;
    }

    private List<RecipeIngredient> createRecipeIngredient(Long id, Ingredient ingredient, Recipe recipe, IngredientDetail ingredientDetail){
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setId(id);
        recipeIngredient.setIngredientDetail(ingredientDetail);
        List<RecipeIngredient> recipeIngredients = new ArrayList<>();
        recipeIngredients.add(recipeIngredient);
        return recipeIngredients;
    }

    private Ingredient createIngredient(Long id, String ingredientName, List<RecipeIngredient> recipeIngredients){
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        ingredient.setName(ingredientName);
        ingredient.setRecipeIngredients(recipeIngredients);
        ingredient.setShoppingItems(new ArrayList<>());
        return ingredient;
    }

    private IngredientDetail createIngredientDetail(Long id, int quantity, String unit, List<RecipeIngredient> recipeIngredients){
        IngredientDetail ingredientDetail = new IngredientDetail();
        ingredientDetail.setId(id);
        ingredientDetail.setQuantity(quantity);
        ingredientDetail.setUnit(unit);
        ingredientDetail.setRecipeIngredients(recipeIngredients);
        return ingredientDetail;
    }
}
