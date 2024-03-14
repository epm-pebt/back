package com.epam.ecobites.service;

import com.epam.ecobites.converter.RecipeStepConverter;
import com.epam.ecobites.data.RecipeStepRepository;
import com.epam.ecobites.domain.RecipeStep;
import com.epam.ecobites.dto.RecipeStepDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeStepService implements CrudService<RecipeStepDTO, Long> {
    private RecipeStepRepository recipeStepRepository;
    private RecipeStepConverter recipeStepConverter;

    public RecipeStepService(RecipeStepRepository recipeStepRepository, RecipeStepConverter recipeStepConverter) {
        this.recipeStepRepository = recipeStepRepository;
        this.recipeStepConverter = recipeStepConverter;
    }

    @Override
    public RecipeStepDTO create(RecipeStepDTO recipeStepDto) {
        RecipeStep recipeStep = recipeStepConverter.fromDTO(recipeStepDto);
        RecipeStep savedRecipeStep = recipeStepRepository.save(recipeStep);
        return recipeStepConverter.toDTO(savedRecipeStep);
    }

    @Override
    public RecipeStepDTO get(Long id) {
        RecipeStep recipeStep = recipeStepRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("RecipeStep with id " + id + " not found"));
        return recipeStepConverter.toDTO(recipeStep);
    }

    @Override
    public List<RecipeStepDTO> getAll() {
        List<RecipeStep> recipeSteps = recipeStepRepository.findAll();
        return recipeSteps.stream().map(recipeStepConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public RecipeStepDTO update(Long id, RecipeStepDTO updatedRecipeStepDto) {
        validateIds(id, updatedRecipeStepDto);
        RecipeStep existingRecipeStep = findRecipeStepById(id);
        RecipeStep updatedRecipeStep = recipeStepConverter.fromDTO(updatedRecipeStepDto);
        updateRecipeStepFields(existingRecipeStep, updatedRecipeStep);
        RecipeStep savedRecipeStep = recipeStepRepository.save(existingRecipeStep);
        return recipeStepConverter.toDTO(savedRecipeStep);
    }

    private void validateIds(Long id, RecipeStepDTO updatedRecipeStepDto) {
        if (!id.equals(updatedRecipeStepDto.getId())) {
            throw new IllegalArgumentException("ID in the path and ID in the RecipeStep object must be the same");
        }
    }

    private RecipeStep findRecipeStepById(Long id) {
        return recipeStepRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("RecipeStep with id " + id + " not found"));
    }

    private void updateRecipeStepFields(RecipeStep existingRecipeStep, RecipeStep updatedRecipeStep) {
        existingRecipeStep.setNumber(updatedRecipeStep.getNumber());
        existingRecipeStep.setTitle(updatedRecipeStep.getTitle());
        existingRecipeStep.setDescription(updatedRecipeStep.getDescription());
        existingRecipeStep.setImage(updatedRecipeStep.getImage());
        existingRecipeStep.setRecipe(updatedRecipeStep.getRecipe());
    }

    @Override
    public void delete(Long id) {
        if (!recipeStepRepository.existsById(id)) {
            throw new NotFoundException("RecipeStep with id " + id + " not found");
        }
        recipeStepRepository.deleteById(id);
    }
}
