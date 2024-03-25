package com.epam.ecobites.service;

import com.epam.ecobites.domain.converter.RecipeConvertToDTO;
import com.epam.ecobites.domain.converter.RecipeConvertToEntity;
import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.dto.RecipeDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService implements CrudService<RecipeDTO, Long> {
    private RecipeRepository recipeRepository;
    private RecipeConvertToDTO convertToDTO;
    private RecipeConvertToEntity convertToEntity;

    public RecipeService(RecipeRepository recipeRepository, RecipeConvertToDTO convertToDTO, RecipeConvertToEntity convertToEntity) {
        this.recipeRepository = recipeRepository;
        this.convertToDTO = convertToDTO;
        this.convertToEntity = convertToEntity;
    }

    @Override
    public RecipeDTO create(RecipeDTO recipeDto) {
        nullCheck(recipeDto);
        Recipe recipe = convertToEntity.convert(recipeDto);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return convertToDTO.convert(savedRecipe);
    }

    @Override
    public RecipeDTO getById(Long id) {
        nullCheck(id);
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe with id " + id + " not found"));
        return convertToDTO.convert(recipe);
    }

    @Override
    public List<RecipeDTO> getAll() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream().map(convertToDTO::convert).collect(Collectors.toList());
    }

    @Override
    public RecipeDTO update(Long id, RecipeDTO updatedRecipeDto) {
        nullCheck(id);
        nullCheck(updatedRecipeDto);
        validateIds(id, updatedRecipeDto);
        Recipe existingRecipe = findRecipeById(id);
        Recipe updatedRecipe = convertToEntity.convert(updatedRecipeDto);
        updateRecipeFields(existingRecipe, updatedRecipe);
        Recipe savedRecipe = recipeRepository.save(existingRecipe);
        return convertToDTO.convert(savedRecipe);
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
        nullCheck(id);
        if (!recipeRepository.existsById(id)) {
            throw new NotFoundException("Recipe with id " + id + " not found");
        }
        recipeRepository.deleteById(id);
    }

    private void nullCheck(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
    }
}
