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

    private static final Long RECIPE_ID_1 = 1L;
    private static final Long RECIPE_ID_2 = 2L;
    private static final Long RECIPE_ID_3 = 3L;
    private static final String RECIPE_NAME_1 = "Recipe1";
    private static final String RECIPE_NAME_2 = "Recipe2";
    private static final String RECIPE_NAME_3 = "Recipe3";
    private static final int RECIPE_TIME_1 = 30;
    private static final int RECIPE_TIME_2 = 20;
    private static final int RECIPE_TIME_3 = 10;
    private static final int EXPECTED_SIZE_2 = 2;
    private static final int EXPECTED_SIZE_3 = 3;
    private static final int TO_RECIPE_DTO_FIRST_ARGUMENT = 0;
    private static final String SEARCH_TEXT = "Rec";

    @DisplayName("Test getting all recipes")
    @Test
    void testGetAllRecipes() {
        Recipe recipe1 = createRecipe(RECIPE_ID_1,RECIPE_NAME_1,RECIPE_TIME_1);
        Recipe recipe2 = createRecipe(RECIPE_ID_2,RECIPE_NAME_2,RECIPE_TIME_2);
        List<Recipe> recipeList = Arrays.asList(recipe1, recipe2);

        when(recipeRepository.findAll()).thenReturn(recipeList);
        when(recipeMapper.toRecipeDto(any(Recipe.class)))
                .thenAnswer(i -> createRecipeDto(
                        ((Recipe) i.getArgument(TO_RECIPE_DTO_FIRST_ARGUMENT)).getName(),
                        ((Recipe) i.getArgument(TO_RECIPE_DTO_FIRST_ARGUMENT)).getTime()));

        List<RecipeDto> result = recipeServiceImpl.getAll();

        assertEquals(EXPECTED_SIZE_2, result.size());
    }

    @DisplayName("Test finding top 10 recipes by least cooking time")
    @Test
    void testFindTop10ByLeastCookingTime() {
        List<Recipe> recipes = Arrays.asList(
                createRecipe(RECIPE_ID_3, RECIPE_NAME_3, RECIPE_TIME_3),
                createRecipe(RECIPE_ID_2, RECIPE_NAME_2, RECIPE_TIME_2),
                createRecipe(RECIPE_ID_1, RECIPE_NAME_1, RECIPE_TIME_1)
        );

        Page<Recipe> page = new PageImpl<>(recipes);

        when(recipeRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(recipeMapper.toRecipeDto(any(Recipe.class)))
                .thenAnswer(i -> createRecipeDto(
                        ((Recipe) i.getArgument(TO_RECIPE_DTO_FIRST_ARGUMENT)).getName(),
                        ((Recipe) i.getArgument(TO_RECIPE_DTO_FIRST_ARGUMENT)).getTime()));

        List<RecipeDto> result = recipeServiceImpl.findTop10ByLeastCookingTime();

        assertEquals(EXPECTED_SIZE_3, result.size());
        assertEquals(RECIPE_NAME_3,result.getFirst().getName());
        assertEquals(RECIPE_NAME_1,result.getLast().getName());
    }

    @DisplayName("Test searching recipes by name using 3 or more character and press enter")
    @Test
    void testSearchRecipes() {
        List<Recipe> recipes = Arrays.asList(
                createRecipe(RECIPE_ID_1, RECIPE_NAME_1, RECIPE_TIME_1),
                createRecipe(RECIPE_ID_2, RECIPE_NAME_2, RECIPE_TIME_2)
        );

        when(recipeRepository.findByNameLike(SEARCH_TEXT)).thenReturn(recipes);
        when(recipeMapper.toRecipeDto(any(Recipe.class)))
                .thenAnswer(i -> createRecipeDto(
                        ((Recipe) i.getArgument(TO_RECIPE_DTO_FIRST_ARGUMENT)).getName(),
                        ((Recipe) i.getArgument(TO_RECIPE_DTO_FIRST_ARGUMENT)).getTime()));

        List<RecipeDto> result = recipeServiceImpl.searchRecipes(SEARCH_TEXT);

        assertEquals(EXPECTED_SIZE_2, result.size());
        assertEquals(SEARCH_TEXT, RECIPE_NAME_1.substring(0,3));
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
