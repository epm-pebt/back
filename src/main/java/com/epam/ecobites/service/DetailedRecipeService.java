package com.epam.ecobites.service;

import com.epam.ecobites.domain.ShoppingItem;
import com.epam.ecobites.domain.dto.DetailedRecipeDto;

import java.util.List;

public interface DetailedRecipeService {
    DetailedRecipeDto getRecipeDetails(String recipeName);
    //List<ShoppingItem> addToGrocery(String recipeName, String username);
}
