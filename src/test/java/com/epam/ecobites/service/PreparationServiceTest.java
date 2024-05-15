package com.epam.ecobites.service;

import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.data.RecipeStepRepository;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.RecipeStep;
import com.epam.ecobites.domain.dto.RecipeStepDto;
import com.epam.ecobites.domain.mapper.RecipeStepMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PreparationServiceTest {

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private RecipeStepRepository recipeStepRepository;
    @Mock
    private RecipeStepMapper recipeStepMapper;
    @InjectMocks
    private PreparationServiceImpl preparationService;

    private static final Long RECIPE_ID_3 = 1L;
    private static final String RECIPE_NAME_1 = "Recipe1";
    private static final int RECIPE_TIME_1 = 30;

    @Test
    @DisplayName("Test get preparation section")
    void testPreparationSectionGetting(){
        Recipe recipe = createRecipe(RECIPE_ID_3, RECIPE_NAME_1, RECIPE_TIME_1);
        List<RecipeStep> recipeSteps = createRecipeSteps(recipe);
        List<RecipeStepDto> recipeStepDtos = createRecipeStepDtos();

        when(recipeRepository.findByName(any(String.class))).thenReturn(Optional.of(recipe));
        when(recipeStepRepository.findByRecipeId(any(Long.class))).thenReturn(recipeSteps);
        when(recipeStepMapper.toRecipeStepDto(any(RecipeStep.class))).thenReturn(
                recipeStepDtos.get(0),recipeStepDtos.get(1), recipeStepDtos.get(2)
        );

        assertEquals(recipeStepDtos, preparationService.getRecipeSteps(recipe.getName()));
    }

    private List<RecipeStepDto> createRecipeStepDtos() {
        List<RecipeStepDto> recipeSteps = new ArrayList<>();
        for(int i=1; i<=3; i++){
            RecipeStepDto recipeStepDto = new RecipeStepDto();
            recipeStepDto.setTitle("Step title "+i);
            recipeStepDto.setNumber((short) i);
            recipeStepDto.setDescription("Test Description "+i);
            recipeStepDto.setImage("Test image "+i);
            recipeSteps.add(recipeStepDto);
        }
        return recipeSteps;
    }

    private List<RecipeStep> createRecipeSteps(Recipe recipe) {
        List<RecipeStep> recipeSteps = new ArrayList<>();
        for(int i=1; i<=3; i++){
            RecipeStep recipeStep = new RecipeStep();
            recipeStep.setRecipe(recipe);
            recipeStep.setId(Long.valueOf(i));
            recipeStep.setTitle("Step title "+i);
            recipeStep.setNumber((short) i);
            recipeStep.setDescription("Test Description "+i);
            recipeStep.setImage("Test image "+i);
            recipeSteps.add(recipeStep);
        }
        return recipeSteps;
    }

    private Recipe createRecipe(Long id, String name, int time) {
        Recipe recipe = new Recipe();
        recipe.setId(id);
        recipe.setName(name);
        recipe.setTime(time);
        recipe.setImage("Test Image");
        return recipe;
    }
}
