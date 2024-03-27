package com.epam.ecobites.service;

import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.dto.RecipeDto;
import com.epam.ecobites.domain.mapper.RecipeMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeMapper recipeMapper;

    @InjectMocks
    private RecipeServiceImpl recipeServiceImpl;

    private static final String NAME = "test";
    private static final int TIME = 30;

    @DisplayName("Test getting all recipes")
    @Test
    void testGetAllRecipes() {
        Recipe recipe1 = createRecipe(1L,NAME,TIME);
        Recipe recipe2 = createRecipe(2L,NAME,TIME);
        List<Recipe> recipeList = Arrays.asList(recipe1, recipe2);

        RecipeDto recipeDTO1 = createRecipeDto(NAME,TIME);
        RecipeDto recipeDTO2 = createRecipeDto(NAME,TIME);
        List<RecipeDto> recipeDTOList = Arrays.asList(recipeDTO1, recipeDTO2);

        when(recipeRepository.findAll()).thenReturn(recipeList);
        when(recipeMapper.toRecipeDto(recipe1)).thenReturn(recipeDTO1);
        when(recipeMapper.toRecipeDto(recipe2)).thenReturn(recipeDTO2);

        List<RecipeDto> result = recipeServiceImpl.getAll();

        assertEquals(recipeDTOList, result);
    }

    @DisplayName("Test finding top 10 recipes by least cooking time")
    @Test
    void testFindTop10ByLeastCookingTime() {
        List<Recipe> recipes = Arrays.asList(
                createRecipe(1L, "Recipe1", 30),
                createRecipe(2L, "Recipe2", 20),
                createRecipe(3L, "Recipe3", 10)
        );

        Page<Recipe> page = new PageImpl<>(recipes);

        when(recipeRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(recipeMapper.toRecipeDto(any(Recipe.class)))
                .thenAnswer(i -> createRecipeDto(
                        ((Recipe) i.getArguments()[0]).getName(),
                        ((Recipe) i.getArguments()[0]).getTime()));

        List<RecipeDto> result = recipeServiceImpl.findTop10ByLeastCookingTime();

        assertEquals(3, result.size());
        assertEquals("Recipe1", result.get(0).getName());
        assertEquals("Recipe2", result.get(1).getName());
        assertEquals("Recipe3", result.get(2).getName());
    }

    private Recipe createRecipe(Long id, String name, int time) {
        Recipe recipe = new Recipe();
        recipe.setId(id);
        recipe.setName(name);
        recipe.setTime(time);
        recipe.setImage("Test Image");
        return recipe;
    }

    private RecipeDto createRecipeDto(String name, int time) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName(name);
        recipeDto.setTime(time);
        recipeDto.setImage("Test Image");
        return recipeDto;
    }
}
