package com.epam.ecobites.converter;

import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.RecipeStep;
import com.epam.ecobites.dto.RecipeStepDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class RecipeStepConverter implements EntityConverter<RecipeStepDTO, RecipeStep> {
    private RecipeRepository recipeRepository;

    public RecipeStepConverter(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public RecipeStepDTO convertToDTO(RecipeStep recipeStep) {
        return RecipeStepDTO.builder()
                .id(recipeStep.getId())
                .number(recipeStep.getNumber())
                .title(recipeStep.getTitle())
                .description(recipeStep.getDescription())
                .image(recipeStep.getImage())
                .recipeId(recipeStep.getRecipe().getId())
                .build();
    }

    @Override
    public RecipeStep convertToEntity(RecipeStepDTO recipeStepDto) {
        Recipe recipe = recipeRepository.findById(recipeStepDto.getRecipeId())
                .orElseThrow(() -> new NotFoundException("Recipe with id " + recipeStepDto.getRecipeId() + " not found"));

        RecipeStep recipeStep = new RecipeStep();
        recipeStep.setId(recipeStepDto.getId());
        recipeStep.setNumber(recipeStepDto.getNumber());
        recipeStep.setTitle(recipeStepDto.getTitle());
        recipeStep.setDescription(recipeStepDto.getDescription());
        recipeStep.setImage(recipeStepDto.getImage());
        recipeStep.setRecipe(recipe);
        return recipeStep;
    }
}
