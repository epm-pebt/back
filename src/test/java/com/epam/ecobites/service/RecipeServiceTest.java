package com.epam.ecobites.service;

import com.epam.ecobites.domain.converter.RecipeConvertToDTO;
import com.epam.ecobites.domain.converter.RecipeConvertToEntity;
import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.domain.DietCategory;
import com.epam.ecobites.domain.DishType;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.dto.RecipeDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeConvertToDTO convertToDTO;

    @Mock
    private RecipeConvertToEntity convertToEntity;

    @InjectMocks
    private RecipeService recipeService;

    private static final Long ID = 1L;
    private static final Long INVALID_ID = -1L;
    private static final String NAME = "test";
    private static final String UPDATED_NAME = "testOther";

    @DisplayName("Test creating a recipe with valid data")
    @Test
    public void testCreateRecipe() {
        RecipeDTO recipeDTO = createRecipeDTO(ID,NAME);
        Recipe recipe = createRecipe(ID,NAME);

        when(convertToEntity.convert(recipeDTO)).thenReturn(recipe);
        when(recipeRepository.save(recipe)).thenReturn(recipe);
        when(convertToDTO.convert(recipe)).thenReturn(recipeDTO);

        RecipeDTO result = recipeService.create(recipeDTO);

        assertEquals(recipeDTO, result);
        verify(recipeRepository).save(recipe);
    }

    @DisplayName("Test getting a recipe by id when the recipe exists")
    @Test
    public void testGetRecipe_Successful() {
        Recipe recipe = createRecipe(ID,NAME);
        RecipeDTO recipeDTO = createRecipeDTO(ID,NAME);

        when(recipeRepository.findById(ID)).thenReturn(Optional.of(recipe));
        when(convertToDTO.convert(recipe)).thenReturn(recipeDTO);

        RecipeDTO result = recipeService.getById(ID);

        assertEquals(recipeDTO, result);
    }

    @DisplayName("Test getting a recipe by id when the recipe does not exist")
    @Test
    public void testGetRecipe_Failed() {
        when(recipeRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> recipeService.getById(ID));
    }

    @DisplayName("Test getting all recipes")
    @Test
    public void testGetAllRecipes() {
        Recipe recipe1 = createRecipe(ID,NAME);
        Recipe recipe2 = createRecipe(2L,NAME);
        List<Recipe> recipeList = Arrays.asList(recipe1, recipe2);

        RecipeDTO recipeDTO1 = createRecipeDTO(ID,NAME);
        RecipeDTO recipeDTO2 = createRecipeDTO(2L,NAME);
        List<RecipeDTO> recipeDTOList = Arrays.asList(recipeDTO1, recipeDTO2);

        when(recipeRepository.findAll()).thenReturn(recipeList);
        when(convertToDTO.convert(recipe1)).thenReturn(recipeDTO1);
        when(convertToDTO.convert(recipe2)).thenReturn(recipeDTO2);

        List<RecipeDTO> result = recipeService.getAll();

        assertEquals(recipeDTOList, result);
    }

    @DisplayName("Test updating a recipe when the recipe exists")
    @Test
    public void testUpdateRecipe_Successful() {
        RecipeDTO updatedRecipeDTO = createRecipeDTO(ID,UPDATED_NAME);
        Recipe existingRecipe = createRecipe(ID,NAME);
        Recipe updatedRecipe = createRecipe(ID,UPDATED_NAME);

        when(recipeRepository.findById(ID)).thenReturn(Optional.of(existingRecipe));
        when(convertToEntity.convert(updatedRecipeDTO)).thenReturn(updatedRecipe);
        when(recipeRepository.save(existingRecipe)).thenReturn(updatedRecipe);
        when(convertToDTO.convert(updatedRecipe)).thenReturn(updatedRecipeDTO);

        RecipeDTO result = recipeService.update(ID, updatedRecipeDTO);

        assertEquals(updatedRecipeDTO, result);
        verify(recipeRepository).save(existingRecipe);
    }

    @DisplayName("Test updating a recipe when the recipe does not exist")
    @Test
    public void testUpdateRecipe_Failed() {
        RecipeDTO recipeDTO = createRecipeDTO(ID,NAME);

        when(recipeRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> recipeService.update(ID, recipeDTO));
    }

    @DisplayName("Test deleting a recipe when the recipe exists")
    @Test
    public void testDeleteRecipe_Successful() {
        when(recipeRepository.existsById(ID)).thenReturn(true);

        recipeService.delete(ID);

        verify(recipeRepository).deleteById(ID);
    }

    @DisplayName("Test deleting a recipe when the recipe does not exist")
    @Test
    public void testDeleteRecipe_Failed() {
        when(recipeRepository.existsById(ID)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> recipeService.delete(ID));
    }

    @DisplayName("Test creating a recipe with null data")
    @Test
    public void testCreateRecipe_NullRecipeDTO() {
        assertThrows(IllegalArgumentException.class, () -> recipeService.create(null));
    }

    @DisplayName("Test updating a recipe with null data")
    @Test
    public void testUpdateRecipe_NullRecipeDTO() {
        assertThrows(IllegalArgumentException.class, () -> recipeService.update(ID, null));
    }

    @DisplayName("Test updating a recipe with null id")
    @Test
    public void testUpdateRecipe_NullId() {
        RecipeDTO recipeDTO = createRecipeDTO(ID,NAME);
        assertThrows(IllegalArgumentException.class, () -> recipeService.update(null, recipeDTO));
    }

    @DisplayName("Test deleting a recipe with null id")
    @Test
    public void testDeleteRecipe_NullId() {
        assertThrows(IllegalArgumentException.class, () -> recipeService.delete(null));
    }

    @DisplayName("Test getting a recipe with null id")
    @Test
    public void testGetRecipe_NullId() {
        assertThrows(IllegalArgumentException.class, () -> recipeService.getById(null));
    }

    @DisplayName("Test deleting a recipe with an invalid id")
    @Test
    public void testDeleteRecipeWithInvalidId() {
        assertThrows(NotFoundException.class, () -> recipeService.delete(INVALID_ID));
    }

    private Recipe createRecipe(Long id, String name) {
        Recipe recipe = new Recipe();
        recipe.setId(id);
        recipe.setName(name);
        recipe.setDishType(DishType.MAIN_COURSE);
        recipe.setDietCategory(DietCategory.VEGETARIAN);
        recipe.setTime(30);
        recipe.setSummary("Test Summary");
        recipe.setImage("Test Image");
        return recipe;
    }

    private RecipeDTO createRecipeDTO(Long id, String name) {
        return RecipeDTO.builder()
                .id(id)
                .name(name)
                .dishType(DishType.MAIN_COURSE)
                .dietCategory(DietCategory.VEGETARIAN)
                .time(30)
                .summary("Test Summary")
                .image("Test Image")
                .build();
    }
}