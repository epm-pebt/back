package com.epam.ecobites.controller;

import com.epam.ecobites.domain.dto.RecipeDto;
import com.epam.ecobites.service.RecipeService;
import com.epam.ecobites.service.RecipeServiceImpl;
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
    public RecipeController(RecipeServiceImpl recipeServiceImpl) {
        this.recipeService = recipeServiceImpl;
    }


    @GetMapping("/api/v1/recipes")
    public ResponseEntity<List<RecipeDto>> getAllRecipes(){
        return new ResponseEntity<>(recipeService.getAll(), HttpStatusCode.valueOf(200));
    }
}
