package com.epam.ecobites.service;

import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.dto.RecipeDto;
import com.epam.ecobites.domain.mapper.RecipeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService<RecipeDto> {
    private RecipeRepository recipeRepository;
    private RecipeMapper recipeMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public List<RecipeDto> getAll() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream().map(recipeMapper::toRecipeDto).toList();
    }

    public List<RecipeDto> findTop10ByLeastCookingTime() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("time").ascending());
        Page<Recipe> page = recipeRepository.findAll(pageRequest);
        return page.getContent().stream().map(recipeMapper::toRecipeDto).toList();
    }

    @Override
    public List<RecipeDto> searchRecipes(String name) {
        if (isLengthOfRecipeNameCorrect(name)) {
            return getRecipeDtoList(name);
        }
        return Collections.emptyList();
    }

    private boolean isLengthOfRecipeNameCorrect(String name) {
        return name.length() >= 3;
    }

    private List<RecipeDto> getRecipeDtoList(String name) {
        List<Recipe> recipeList = recipeRepository.findByNameLike(name);
        return recipeList.stream().map(recipeMapper::toRecipeDto).toList();
    }
}

