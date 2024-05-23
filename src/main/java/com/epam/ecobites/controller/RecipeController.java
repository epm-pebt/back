package com.epam.ecobites.controller;

import com.epam.ecobites.domain.dto.DetailedRecipeDto;
import com.epam.ecobites.domain.dto.RecipeDto;
import com.epam.ecobites.service.DetailedRecipeService;
import com.epam.ecobites.service.DetailedRecipeServiceImpl;
import com.epam.ecobites.service.RecipeService;
import com.epam.ecobites.service.RecipeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecipeController {

    public final RecipeService<RecipeDto> recipeService;

    public final DetailedRecipeService detailedRecipeService;

    @Autowired
    public RecipeController(RecipeServiceImpl recipeServiceImpl, DetailedRecipeServiceImpl detailedRecipeServiceImpl) {
        this.recipeService = recipeServiceImpl;
        this.detailedRecipeService = detailedRecipeServiceImpl;
    }

    @GetMapping("/api/v1/recipes")
    public ResponseEntity<List<RecipeDto>> getAllRecipes(){
        return new ResponseEntity<>(recipeService.getAll(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/api/v1/searchRecipes")
    public ResponseEntity<List<RecipeDto>> searchRecipes(@RequestParam String name) {
        return new ResponseEntity<>(recipeService.searchRecipes(name), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/api/v1/topDetails")
    public ResponseEntity<DetailedRecipeDto> getTopDetails(@RequestParam String recipeName) {
        return new ResponseEntity<>(detailedRecipeService.getRecipeDetails(recipeName),
            HttpStatusCode.valueOf(200));
    }
}
