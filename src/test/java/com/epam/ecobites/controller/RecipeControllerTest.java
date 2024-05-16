package com.epam.ecobites.controller;

import com.epam.ecobites.domain.dto.RecipeDto;
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

    @BeforeEach
    public void setup(){
        this.systemUnderTest = new RecipeController((RecipeServiceImpl) this.recipeService);
    }

    @Test
    void testGetAllRecipes() {
        List<RecipeDto> recipeDtos = new ArrayList<>();
        recipeDtos.add(new RecipeDto(0,"food1",30, "url1","LUNCH", "VEGAN"));
        recipeDtos.add(new RecipeDto(1,"food2",40, "url2", "LUNCH", "VEGAN"));
        recipeDtos.add(new RecipeDto(2,"food3",50, "url3", "LUNCH", "VEGAN"));
        recipeDtos.add(new RecipeDto(3,"food4",10, "url4", "LUNCH","VEGAN"));
        ResponseEntity<List<RecipeDto>> result =
                new ResponseEntity<>(recipeDtos, HttpStatusCode.valueOf(200));

        when(recipeService.getAll()).thenReturn(recipeDtos);

        assertEquals(systemUnderTest.getAllRecipes(), result);
    }

    @Test
    void testSearchRecipes() {
        List<RecipeDto> recipes = new ArrayList<>();
        recipes.add(new RecipeDto("food", 10, "url1"));
        recipes.add(new RecipeDto("food", 20, "url2"));
        recipes.add(new RecipeDto("food", 30, "url3"));

        when(recipeService.searchRecipes("food")).thenReturn(recipes);

        ResponseEntity<List<RecipeDto>> expected = new ResponseEntity<>(recipes, HttpStatusCode.valueOf(200));
        ResponseEntity<List<RecipeDto>> actual = systemUnderTest.searchRecipes("food");
        assertEquals(expected, actual);
    }
}
