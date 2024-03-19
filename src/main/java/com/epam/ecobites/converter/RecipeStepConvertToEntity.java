package com.epam.ecobites.converter;

import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.RecipeStep;
import com.epam.ecobites.dto.RecipeStepDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeStepConvertToEntity implements Converter<RecipeStepDTO, RecipeStep> {
    private RecipeRepository recipeRepository;

    public RecipeStepConvertToEntity(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public RecipeStep convert(RecipeStepDTO source) {
        Recipe recipe = recipeRepository.findById(source.getRecipeId())
                .orElseThrow(() -> new NotFoundException("Recipe with id " + source.getRecipeId() + " not found"));

        RecipeStep recipeStep = new RecipeStep();
        recipeStep.setId(source.getId());
        recipeStep.setNumber(source.getNumber());
        recipeStep.setTitle(source.getTitle());
        recipeStep.setDescription(source.getDescription());
        recipeStep.setImage(source.getImage());
        recipeStep.setRecipe(recipe);
        return recipeStep;
    }
}
