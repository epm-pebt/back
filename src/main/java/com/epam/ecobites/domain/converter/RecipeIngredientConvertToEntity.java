package com.epam.ecobites.domain.converter;

import com.epam.ecobites.data.IngredientDetailRepository;
import com.epam.ecobites.data.IngredientRepository;
import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.domain.Ingredient;
import com.epam.ecobites.domain.IngredientDetail;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.RecipeIngredient;
import com.epam.ecobites.domain.dto.RecipeIngredientDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeIngredientConvertToEntity implements Converter<RecipeIngredientDTO, RecipeIngredient> {
    private RecipeRepository recipeRepository;
    private IngredientRepository ingredientRepository;
    private IngredientDetailRepository ingredientDetailRepository;

    public RecipeIngredientConvertToEntity(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, IngredientDetailRepository ingredientDetailRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientDetailRepository = ingredientDetailRepository;
    }

    @Override
    public RecipeIngredient convert(RecipeIngredientDTO source) {
        Recipe recipe = recipeRepository.findById(source.getRecipeId())
                .orElseThrow(() -> new NotFoundException("Recipe with id " + source.getRecipeId() + " not found"));
        Ingredient ingredient = ingredientRepository.findById(source.getIngredientId())
                .orElseThrow(() -> new NotFoundException("Ingredient with id " + source.getIngredientId() + " not found"));
        IngredientDetail ingredientDetail = ingredientDetailRepository.findById(source.getIngredientDetailId())
                .orElseThrow(() -> new NotFoundException("IngredientDetail with id " + source.getIngredientDetailId() + " not found"));

        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setId(source.getId());
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setIngredientDetail(ingredientDetail);
        return recipeIngredient;
    }
}
