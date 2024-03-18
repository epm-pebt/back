package com.epam.ecobites.service;

import com.epam.ecobites.converter.RecipeConverter;
import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.dto.RecipeDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService implements CrudService<RecipeDTO, Long> {
    private RecipeRepository recipeRepository;
    private RecipeConverter recipeConverter;

    public RecipeService(RecipeRepository recipeRepository, RecipeConverter recipeConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeConverter = recipeConverter;
    }

    @Override
    public RecipeDTO create(RecipeDTO recipeDto) {
        Recipe recipe = recipeConverter.convertToEntity(recipeDto);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return recipeConverter.convertToDTO(savedRecipe);
    }

    @Override
    public RecipeDTO get(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe with id " + id + " not found"));
        return recipeConverter.convertToDTO(recipe);
    }

    @Override
    public List<RecipeDTO> getAll() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream().map(recipeConverter::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public RecipeDTO update(Long id, RecipeDTO updatedRecipeDto) {
        validateIds(id, updatedRecipeDto);
        Recipe existingRecipe = findRecipeById(id);
        Recipe updatedRecipe = recipeConverter.convertToEntity(updatedRecipeDto);
        updateRecipeFields(existingRecipe, updatedRecipe);
        Recipe savedRecipe = recipeRepository.save(existingRecipe);
        return recipeConverter.convertToDTO(savedRecipe);
    }

    private void validateIds(Long id, RecipeDTO updatedRecipeDto) {
        if (!id.equals(updatedRecipeDto.getId())) {
            throw new IllegalArgumentException("ID in the path and ID in the Recipe object must be the same");
        }
    }

    private Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe with id " + id + " not found"));
    }

    private void updateRecipeFields(Recipe existingRecipe, Recipe updatedRecipe) {
        existingRecipe.setName(updatedRecipe.getName());
        existingRecipe.setDishType(updatedRecipe.getDishType());
        existingRecipe.setDietCategory(updatedRecipe.getDietCategory());
        existingRecipe.setTime(updatedRecipe.getTime());
        existingRecipe.setSummary(updatedRecipe.getSummary());
        existingRecipe.setImage(updatedRecipe.getImage());
    }

    @Override
    public void delete(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new NotFoundException("Recipe with id " + id + " not found");
        }
        recipeRepository.deleteById(id);
    }
}
