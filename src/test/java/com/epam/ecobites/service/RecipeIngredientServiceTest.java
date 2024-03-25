package com.epam.ecobites.service;

import com.epam.ecobites.domain.converter.RecipeIngredientConvertToDTO;
import com.epam.ecobites.domain.converter.RecipeIngredientConvertToEntity;
import com.epam.ecobites.data.RecipeIngredientRepository;
import com.epam.ecobites.domain.*;
import com.epam.ecobites.domain.dto.RecipeIngredientDTO;
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
public class RecipeIngredientServiceTest {

    @Mock
    private RecipeIngredientRepository recipeIngredientRepository;

    @Mock
    private RecipeIngredientConvertToDTO convertToDTO;

    @Mock
    private RecipeIngredientConvertToEntity convertToEntity;

    @InjectMocks
    private RecipeIngredientService recipeIngredientService;

    private static final Long ID = 1L;
    private static final Long INVALID_ID = -1L;
    private static final Recipe RECIPE = new Recipe();
    private static final Recipe UPDATED_RECIPE = new Recipe();
    private static final Long RECIPE_ID = 2L;

    @DisplayName("Test creating a recipe ingredient with valid data")
    @Test
    public void testCreateRecipeIngredient() {
        RecipeIngredientDTO recipeIngredientDTO = createRecipeIngredientDTO(ID, RECIPE_ID);
        RecipeIngredient recipeIngredient = createRecipeIngredient(ID, RECIPE);

        when(convertToEntity.convert(recipeIngredientDTO)).thenReturn(recipeIngredient);
        when(recipeIngredientRepository.save(recipeIngredient)).thenReturn(recipeIngredient);
        when(convertToDTO.convert(recipeIngredient)).thenReturn(recipeIngredientDTO);

        RecipeIngredientDTO result = recipeIngredientService.create(recipeIngredientDTO);

        assertEquals(recipeIngredientDTO, result);
        verify(recipeIngredientRepository).save(recipeIngredient);
    }

    @DisplayName("Test getting a recipe ingredient by id when the recipe ingredient exists")
    @Test
    public void testGetRecipeIngredient_Successful() {
        RecipeIngredient recipeIngredient = createRecipeIngredient(ID, RECIPE);
        RecipeIngredientDTO recipeIngredientDTO = createRecipeIngredientDTO(ID, RECIPE_ID);

        when(recipeIngredientRepository.findById(ID)).thenReturn(Optional.of(recipeIngredient));
        when(convertToDTO.convert(recipeIngredient)).thenReturn(recipeIngredientDTO);

        RecipeIngredientDTO result = recipeIngredientService.getById(ID);

        assertEquals(recipeIngredientDTO, result);
    }

    @DisplayName("Test getting a recipe ingredient by id when the recipe ingredient does not exist")
    @Test
    public void testGetRecipeIngredient_Failed() {
        when(recipeIngredientRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> recipeIngredientService.getById(ID));
    }

    @DisplayName("Test getting all recipe ingredients")
    @Test
    public void testGetAllRecipeIngredients() {
        RecipeIngredient recipeIngredient1 = createRecipeIngredient(ID, RECIPE);
        RecipeIngredient recipeIngredient2 = createRecipeIngredient(2L, RECIPE);
        List<RecipeIngredient> recipeIngredientList = Arrays.asList(recipeIngredient1, recipeIngredient2);

        RecipeIngredientDTO recipeIngredientDTO1 = createRecipeIngredientDTO(ID, RECIPE_ID);
        RecipeIngredientDTO recipeIngredientDTO2 = createRecipeIngredientDTO(2L, RECIPE_ID);
        List<RecipeIngredientDTO> recipeIngredientDTOList = Arrays.asList(recipeIngredientDTO1, recipeIngredientDTO2);

        when(recipeIngredientRepository.findAll()).thenReturn(recipeIngredientList);
        when(convertToDTO.convert(recipeIngredient1)).thenReturn(recipeIngredientDTO1);
        when(convertToDTO.convert(recipeIngredient2)).thenReturn(recipeIngredientDTO2);

        List<RecipeIngredientDTO> result = recipeIngredientService.getAll();

        assertEquals(recipeIngredientDTOList, result);
    }

    @DisplayName("Test updating a recipe ingredient when the recipe ingredient exists")
    @Test
    public void testUpdateRecipeIngredient_Successful() {
        RecipeIngredientDTO updatedRecipeIngredientDTO = createRecipeIngredientDTO(ID, RECIPE_ID);
        RecipeIngredient existingRecipeIngredient = createRecipeIngredient(ID, RECIPE);
        RecipeIngredient updatedRecipeIngredient = createRecipeIngredient(ID, UPDATED_RECIPE);

        when(recipeIngredientRepository.findById(ID)).thenReturn(Optional.of(existingRecipeIngredient));
        when(convertToEntity.convert(updatedRecipeIngredientDTO)).thenReturn(updatedRecipeIngredient);
        when(recipeIngredientRepository.save(existingRecipeIngredient)).thenReturn(updatedRecipeIngredient);
        when(convertToDTO.convert(updatedRecipeIngredient)).thenReturn(updatedRecipeIngredientDTO);

        RecipeIngredientDTO result = recipeIngredientService.update(ID, updatedRecipeIngredientDTO);

        assertEquals(updatedRecipeIngredientDTO, result);
        verify(recipeIngredientRepository).save(existingRecipeIngredient);
    }

    @DisplayName("Test updating a recipe ingredient when the recipe ingredient does not exist")
    @Test
    public void testUpdateRecipeIngredient_Failed() {
        RecipeIngredientDTO recipeIngredientDTO = createRecipeIngredientDTO(ID, RECIPE_ID);

        when(recipeIngredientRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> recipeIngredientService.update(ID, recipeIngredientDTO));
    }

    @DisplayName("Test deleting a recipe ingredient when the recipe ingredient exists")
    @Test
    public void testDeleteRecipeIngredient_Successful() {
        when(recipeIngredientRepository.existsById(ID)).thenReturn(true);

        recipeIngredientService.delete(ID);

        verify(recipeIngredientRepository).deleteById(ID);
    }

    @DisplayName("Test deleting a recipe ingredient when the recipe ingredient does not exist")
    @Test
    public void testDeleteRecipeIngredient_Failed() {
        when(recipeIngredientRepository.existsById(ID)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> recipeIngredientService.delete(ID));
    }

    @DisplayName("Test creating a recipe ingredient with null data")
    @Test
    public void testCreateRecipeIngredient_NullRecipeIngredientDTO() {
        assertThrows(IllegalArgumentException.class, () -> recipeIngredientService.create(null));
    }

    @DisplayName("Test updating a recipe ingredient with null data")
    @Test
    public void testUpdateRecipeIngredient_NullRecipeIngredientDTO() {
        assertThrows(IllegalArgumentException.class, () -> recipeIngredientService.update(ID, null));
    }

    @DisplayName("Test updating a recipe ingredient with null id")
    @Test
    public void testUpdateRecipeIngredient_NullId() {
        RecipeIngredientDTO recipeIngredientDTO = createRecipeIngredientDTO(ID, RECIPE_ID);
        assertThrows(IllegalArgumentException.class, () -> recipeIngredientService.update(null, recipeIngredientDTO));
    }

    @DisplayName("Test deleting a recipe ingredient with null id")
    @Test
    public void testDeleteRecipeIngredient_NullId() {
        assertThrows(IllegalArgumentException.class, () -> recipeIngredientService.delete(null));
    }

    @DisplayName("Test getting a recipe ingredient with null id")
    @Test
    public void testGetRecipeIngredient_NullId() {
        assertThrows(IllegalArgumentException.class, () -> recipeIngredientService.getById(null));
    }

    @DisplayName("Test deleting a recipe ingredient with an invalid id")
    @Test
    public void testDeleteRecipeIngredientWithInvalidId() {
        assertThrows(NotFoundException.class, () -> recipeIngredientService.delete(INVALID_ID));
    }

    private RecipeIngredient createRecipeIngredient(Long id, Recipe recipe) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setId(id);
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(new Ingredient());
        recipeIngredient.setIngredientDetail(new IngredientDetail());
        return recipeIngredient;
    }

    private RecipeIngredientDTO createRecipeIngredientDTO(Long id, Long recipeID) {
        return RecipeIngredientDTO.builder()
                .id(id)
                .recipeId(recipeID)
                .ingredientId(1L)
                .ingredientDetailId(1L)
                .build();
    }
}