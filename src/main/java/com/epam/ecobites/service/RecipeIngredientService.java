package com.epam.ecobites.service;

import com.epam.ecobites.converter.RecipeIngredientConverter;
import com.epam.ecobites.data.RecipeIngredientRepository;
import com.epam.ecobites.domain.RecipeIngredient;
import com.epam.ecobites.dto.RecipeIngredientDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeIngredientService implements CrudService<RecipeIngredientDTO, Long> {
    private RecipeIngredientRepository recipeIngredientRepository;
    private RecipeIngredientConverter recipeIngredientConverter;

    public RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepository, RecipeIngredientConverter recipeIngredientConverter) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeIngredientConverter = recipeIngredientConverter;
    }

    @Override
    public RecipeIngredientDTO create(RecipeIngredientDTO recipeIngredientDto) {
        RecipeIngredient recipeIngredient = recipeIngredientConverter.convertToEntity(recipeIngredientDto);
        RecipeIngredient savedRecipeIngredient = recipeIngredientRepository.save(recipeIngredient);
        return recipeIngredientConverter.convertToDTO(savedRecipeIngredient);
    }

    @Override
    public RecipeIngredientDTO get(Long id) {
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("RecipeIngredient with id " + id + " not found"));
        return recipeIngredientConverter.convertToDTO(recipeIngredient);
    }

    @Override
    public List<RecipeIngredientDTO> getAll() {
        List<RecipeIngredient> recipeIngredients = recipeIngredientRepository.findAll();
        return recipeIngredients.stream().map(recipeIngredientConverter::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public RecipeIngredientDTO update(Long id, RecipeIngredientDTO updatedRecipeIngredientDto) {
        validateIds(id, updatedRecipeIngredientDto);
        RecipeIngredient existingRecipeIngredient = findRecipeIngredientById(id);
        RecipeIngredient updatedRecipeIngredient = recipeIngredientConverter.convertToEntity(updatedRecipeIngredientDto);
        updateRecipeIngredientFields(existingRecipeIngredient, updatedRecipeIngredient);
        RecipeIngredient savedRecipeIngredient = recipeIngredientRepository.save(existingRecipeIngredient);
        return recipeIngredientConverter.convertToDTO(savedRecipeIngredient);
    }

    private void validateIds(Long id, RecipeIngredientDTO updatedRecipeIngredientDto) {
        if (!id.equals(updatedRecipeIngredientDto.getId())) {
            throw new IllegalArgumentException("ID in the path and ID in the RecipeIngredient object must be the same");
        }
    }

    private RecipeIngredient findRecipeIngredientById(Long id) {
        return recipeIngredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("RecipeIngredient with id " + id + " not found"));
    }

    private void updateRecipeIngredientFields(RecipeIngredient existingRecipeIngredient, RecipeIngredient updatedRecipeIngredient) {
        existingRecipeIngredient.setRecipe(updatedRecipeIngredient.getRecipe());
        existingRecipeIngredient.setIngredient(updatedRecipeIngredient.getIngredient());
        existingRecipeIngredient.setIngredientDetail(updatedRecipeIngredient.getIngredientDetail());
    }

    @Override
    public void delete(Long id) {
        if (!recipeIngredientRepository.existsById(id)) {
            throw new NotFoundException("RecipeIngredient with id " + id + " not found");
        }
        recipeIngredientRepository.deleteById(id);
    }
}
