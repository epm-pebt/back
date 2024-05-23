package com.epam.ecobites.controller;

import com.epam.ecobites.domain.dto.DetailedRecipeDto;
import com.epam.ecobites.domain.dto.IngredientDetailDto;
import com.epam.ecobites.domain.dto.IngredientDto;
import com.epam.ecobites.domain.dto.RecipeDto;
import com.epam.ecobites.domain.dto.RecipeIngredientDto;
import com.epam.ecobites.service.DetailedRecipeService;
import com.epam.ecobites.service.DetailedRecipeServiceImpl;
import com.epam.ecobites.service.RecipeService;
import com.epam.ecobites.service.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RecipeControllerTest {

    public RecipeController systemUnderTest;
    public RecipeService<RecipeDto> recipeService = Mockito.mock(RecipeServiceImpl.class);
    public DetailedRecipeService detailedRecipeService = Mockito.mock(DetailedRecipeServiceImpl.class);

    @BeforeEach
    public void setup(){
        this.systemUnderTest = new RecipeController((RecipeServiceImpl) this.recipeService,
            (DetailedRecipeServiceImpl) this.detailedRecipeService);
    }

    @Test
    void testGetAllRecipes() {
        List<RecipeDto> recipeDtos = new ArrayList<>();
        recipeDtos.add(new RecipeDto(0L,"food1",30, "url1","LUNCH", "VEGAN"));
        recipeDtos.add(new RecipeDto(1L,"food2",40, "url2", "LUNCH", "VEGAN"));
        recipeDtos.add(new RecipeDto(2L,"food3",50, "url3", "LUNCH", "VEGAN"));
        recipeDtos.add(new RecipeDto(3L,"food4",10, "url4", "LUNCH","VEGAN"));
        ResponseEntity<List<RecipeDto>> result =
                new ResponseEntity<>(recipeDtos, HttpStatusCode.valueOf(200));

        when(recipeService.getAll()).thenReturn(recipeDtos);

        assertEquals(systemUnderTest.getAllRecipes(), result);
    }

    @Test
    void testSearchRecipes() {
        List<RecipeDto> recipes = new ArrayList<>();
        recipes.add(new RecipeDto(1L, "food", 10, "url1","LUNCH", "VEGAN"));
        recipes.add(new RecipeDto(2L, "food", 20, "url2", "LUNCH", "VEGAN"));
        recipes.add(new RecipeDto(3L, "food", 30, "url3", "LUNCH", "VEGAN"));

        when(recipeService.searchRecipes("food")).thenReturn(recipes);

        ResponseEntity<List<RecipeDto>> expected = new ResponseEntity<>(recipes, HttpStatusCode.valueOf(200));
        ResponseEntity<List<RecipeDto>> actual = systemUnderTest.searchRecipes("food");
        assertEquals(expected, actual);
    }

    @Test
    void testGetTopDetails() {
        // GIVEN
        IngredientDto ingredientDto = new IngredientDto("testIngredientName");
        IngredientDetailDto ingredientDetailDto = new IngredientDetailDto(1, "ml");
        RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto(ingredientDto, ingredientDetailDto);
        List<RecipeIngredientDto> recipeIngredientDtos = new ArrayList<>();
        recipeIngredientDtos.add(recipeIngredientDto);

        RecipeDto recipeDto = new RecipeDto(1L, "food", 10, "url1",
            "LUNCH", "VEGAN");

        DetailedRecipeDto expectedRecipeDetail = new DetailedRecipeDto();
        expectedRecipeDetail.setRecipeDto(recipeDto);
        expectedRecipeDetail.setRecipeIngredientDtos(recipeIngredientDtos);

        ResponseEntity<DetailedRecipeDto> expectedResponse = new ResponseEntity<>(expectedRecipeDetail,
            HttpStatusCode.valueOf(200));

        Mockito.when(detailedRecipeService.getRecipeDetails("testRecipeName")).thenReturn(expectedRecipeDetail);

        // WHEN
        var actualResponse = systemUnderTest.getTopDetails("testRecipeName");

        // THEN
        assertEquals(expectedResponse, actualResponse);
    }
}
