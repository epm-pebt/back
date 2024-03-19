package com.epam.ecobites.service;

import com.epam.ecobites.converter.RecipeIngredientConvertToDTO;
import com.epam.ecobites.converter.RecipeIngredientConvertToEntity;
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
    private RecipeIngredientConvertToDTO convertToDTO;
    private RecipeIngredientConvertToEntity convertToEntity;

    public RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepository, RecipeIngredientConvertToDTO convertToDTO, RecipeIngredientConvertToEntity convertToEntity) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.convertToDTO = convertToDTO;
        this.convertToEntity = convertToEntity;
    }

    @Override
    public RecipeIngredientDTO create(RecipeIngredientDTO recipeIngredientDto) {
        nullCheck(recipeIngredientDto);
        RecipeIngredient recipeIngredient = convertToEntity.convert(recipeIngredientDto);
        RecipeIngredient savedRecipeIngredient = recipeIngredientRepository.save(recipeIngredient);
        return convertToDTO.convert(savedRecipeIngredient);
    }

    @Override
    public RecipeIngredientDTO getById(Long id) {
        nullCheck(id);
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("RecipeIngredient with id " + id + " not found"));
        return convertToDTO.convert(recipeIngredient);
    }

    @Override
    public List<RecipeIngredientDTO> getAll() {
        List<RecipeIngredient> recipeIngredients = recipeIngredientRepository.findAll();
        return recipeIngredients.stream().map(convertToDTO::convert).collect(Collectors.toList());
    }

    @Override
    public RecipeIngredientDTO update(Long id, RecipeIngredientDTO updatedRecipeIngredientDto) {
        nullCheck(id);
        nullCheck(updatedRecipeIngredientDto);
        validateIds(id, updatedRecipeIngredientDto);
        RecipeIngredient existingRecipeIngredient = findRecipeIngredientById(id);
        RecipeIngredient updatedRecipeIngredient = convertToEntity.convert(updatedRecipeIngredientDto);
        updateRecipeIngredientFields(existingRecipeIngredient, updatedRecipeIngredient);
        RecipeIngredient savedRecipeIngredient = recipeIngredientRepository.save(existingRecipeIngredient);
        return convertToDTO.convert(savedRecipeIngredient);
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
        nullCheck(id);
        if (!recipeIngredientRepository.existsById(id)) {
            throw new NotFoundException("RecipeIngredient with id " + id + " not found");
        }
        recipeIngredientRepository.deleteById(id);
    }

    private void nullCheck(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
    }
}
