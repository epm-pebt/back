package com.epam.ecobites.service;

import java.util.List;

public interface RecipeService<T> {
    List<T> getAll();
    List<T> searchRecipes(String name);
}
