package com.epam.ecobites.service;

import com.epam.ecobites.domain.converter.RecipeStepConvertToDTO;
import com.epam.ecobites.domain.converter.RecipeStepConvertToEntity;
import com.epam.ecobites.data.RecipeStepRepository;
import com.epam.ecobites.domain.RecipeStep;
import com.epam.ecobites.domain.dto.RecipeStepDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeStepService implements CrudService<RecipeStepDTO, Long> {
    private RecipeStepRepository recipeStepRepository;
    private RecipeStepConvertToDTO convertToDTO;
    private RecipeStepConvertToEntity convertToEntity;

    public RecipeStepService(RecipeStepRepository recipeStepRepository, RecipeStepConvertToDTO convertToDTO, RecipeStepConvertToEntity convertToEntity) {
        this.recipeStepRepository = recipeStepRepository;
        this.convertToDTO = convertToDTO;
        this.convertToEntity = convertToEntity;
    }

    @Override
    public RecipeStepDTO create(RecipeStepDTO recipeStepDto) {
        nullCheck(recipeStepDto);
        RecipeStep recipeStep = convertToEntity.convert(recipeStepDto);
        RecipeStep savedRecipeStep = recipeStepRepository.save(recipeStep);
        return convertToDTO.convert(savedRecipeStep);
    }

    @Override
    public RecipeStepDTO getById(Long id) {
        nullCheck(id);
        RecipeStep recipeStep = recipeStepRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("RecipeStep with id " + id + " not found"));
        return convertToDTO.convert(recipeStep);
    }

    @Override
    public List<RecipeStepDTO> getAll() {
        List<RecipeStep> recipeSteps = recipeStepRepository.findAll();
        return recipeSteps.stream().map(convertToDTO::convert).collect(Collectors.toList());
    }

    @Override
    public RecipeStepDTO update(Long id, RecipeStepDTO updatedRecipeStepDto) {
        nullCheck(id);
        nullCheck(updatedRecipeStepDto);
        validateIds(id, updatedRecipeStepDto);
        RecipeStep existingRecipeStep = findRecipeStepById(id);
        RecipeStep updatedRecipeStep = convertToEntity.convert(updatedRecipeStepDto);
        updateRecipeStepFields(existingRecipeStep, updatedRecipeStep);
        RecipeStep savedRecipeStep = recipeStepRepository.save(existingRecipeStep);
        return convertToDTO.convert(savedRecipeStep);
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
        nullCheck(id);
        if (!recipeStepRepository.existsById(id)) {
            throw new NotFoundException("RecipeStep with id " + id + " not found");
        }
        recipeStepRepository.deleteById(id);
    }

    private void nullCheck(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
    }
}
