package com.epam.ecobites.converter;

import com.epam.ecobites.data.IngredientDetailRepository;
import com.epam.ecobites.data.IngredientRepository;
import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.domain.Ingredient;
import com.epam.ecobites.domain.IngredientDetail;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.RecipeIngredient;
import com.epam.ecobites.dto.RecipeIngredientDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class RecipeIngredientConverter implements EntityConverter<RecipeIngredientDTO, RecipeIngredient> {
    private RecipeRepository recipeRepository;
    private IngredientRepository ingredientRepository;
    private IngredientDetailRepository ingredientDetailRepository;

    public RecipeIngredientConverter(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, IngredientDetailRepository ingredientDetailRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientDetailRepository = ingredientDetailRepository;
    }

    @Override
    public RecipeIngredientDTO convertToDTO(RecipeIngredient recipeIngredient) {
        return RecipeIngredientDTO.builder()
                .id(recipeIngredient.getId())
                .recipeId(recipeIngredient.getRecipe().getId())
                .ingredientId(recipeIngredient.getIngredient().getId())
                .ingredientDetailId(recipeIngredient.getIngredientDetail().getId())
                .build();
    }

    @Override
    public RecipeIngredient convertToEntity(RecipeIngredientDTO recipeIngredientDto) {
        Recipe recipe = recipeRepository.findById(recipeIngredientDto.getRecipeId())
                .orElseThrow(() -> new NotFoundException("Recipe with id " + recipeIngredientDto.getRecipeId() + " not found"));
        Ingredient ingredient = ingredientRepository.findById(recipeIngredientDto.getIngredientId())
                .orElseThrow(() -> new NotFoundException("Ingredient with id " + recipeIngredientDto.getIngredientId() + " not found"));
        IngredientDetail ingredientDetail = ingredientDetailRepository.findById(recipeIngredientDto.getIngredientDetailId())
                .orElseThrow(() -> new NotFoundException("IngredientDetail with id " + recipeIngredientDto.getIngredientDetailId() + " not found"));

        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setId(recipeIngredientDto.getId());
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setIngredientDetail(ingredientDetail);
        return recipeIngredient;
    }
}
