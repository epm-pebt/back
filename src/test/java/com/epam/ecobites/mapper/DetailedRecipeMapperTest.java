package com.epam.ecobites.mapper;

import com.epam.ecobites.domain.Ingredient;
import com.epam.ecobites.domain.IngredientDetail;
import com.epam.ecobites.domain.RecipeIngredient;
import com.epam.ecobites.domain.dto.RecipeIngredientDto;
import com.epam.ecobites.domain.mapper.DetailedRecipeMapper;
import com.epam.ecobites.domain.mapper.DetailedRecipeMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DetailedRecipeMapperTest {
    private static final String INGREDIENT_NAME = "Ingredient name";
    private static final Long INGREDIENT_ID = 1L;
    private static final String INGREDIENT_DETAIL_UNIT = "Ingredient name";
    private static final Long INGREDIENT_DETAIL_ID = 1L;
    private static final int INGREDIENT_DETAIL_QUANTITY= 2;
    @Autowired
    DetailedRecipeMapper detailedRecipeMapper;

    @BeforeEach
    void setUp(){
        detailedRecipeMapper = new DetailedRecipeMapperImpl();
    }

    @Test
    @DisplayName("Test Detailed Recipe Mapper Recipe ingredient mapping")
    void testRecipeIngredientMapping(){
        Ingredient ingredient =
                createIngredient(INGREDIENT_ID, INGREDIENT_NAME,new ArrayList<>());
        IngredientDetail ingredientDetail =
                createIngredientDetail(INGREDIENT_DETAIL_ID, INGREDIENT_DETAIL_QUANTITY, INGREDIENT_DETAIL_UNIT, new ArrayList<>());
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setIngredientDetail(ingredientDetail);

        RecipeIngredientDto recipeIngredientDto = detailedRecipeMapper.toRecipeIngredientDto(recipeIngredient);

        assertEquals(ingredient.getName(), recipeIngredientDto.getIngredientDto().getName());
        assertEquals(ingredientDetail.getQuantity(), recipeIngredientDto.getIngredientDetailDto().getQuantity());
        assertEquals(ingredientDetail.getUnit(), recipeIngredientDto.getIngredientDetailDto().getUnit());
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
