package com.epam.ecobites.controller;

import com.epam.ecobites.domain.dto.RecipeDto;
import com.epam.ecobites.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecipeController {

    public final RecipeService<RecipeDto> recipeService;

    @Autowired
    public RecipeController(RecipeService<RecipeDto> recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping("/api/v1/recipes")
    public ResponseEntity<List<RecipeDto>> getAllRecipes(){
        return new ResponseEntity<>(recipeService.getAll(), HttpStatusCode.valueOf(200));
    }
}
