package com.epam.ecobites.service;

import com.epam.ecobites.domain.converter.RecipeStepConvertToDTO;
import com.epam.ecobites.domain.converter.RecipeStepConvertToEntity;
import com.epam.ecobites.data.RecipeStepRepository;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.RecipeStep;
import com.epam.ecobites.domain.dto.RecipeStepDTO;
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
public class RecipeStepServiceTest {

    @Mock
    private RecipeStepRepository recipeStepRepository;

    @Mock
    private RecipeStepConvertToDTO convertToDTO;

    @Mock
    private RecipeStepConvertToEntity convertToEntity;

    @InjectMocks
    private RecipeStepService recipeStepService;

    private static final Long ID = 1L;
    private static final Long INVALID_ID = -1L;
    private static final String TITLE = "test";
    private static final String UPDATED_TITLE = "testOther";

    @DisplayName("Test creating a recipe step with valid data")
    @Test
    public void testCreateRecipeStep() {
        RecipeStepDTO recipeStepDTO = createRecipeStepDTO(ID, TITLE);
        RecipeStep recipeStep = createRecipeStep(ID, TITLE);

        when(convertToEntity.convert(recipeStepDTO)).thenReturn(recipeStep);
        when(recipeStepRepository.save(recipeStep)).thenReturn(recipeStep);
        when(convertToDTO.convert(recipeStep)).thenReturn(recipeStepDTO);

        RecipeStepDTO result = recipeStepService.create(recipeStepDTO);

        assertEquals(recipeStepDTO, result);
        verify(recipeStepRepository).save(recipeStep);
    }

    @DisplayName("Test getting a recipe step by id when the recipe step exists")
    @Test
    public void testGetRecipeStep_Successful() {
        RecipeStep recipeStep = createRecipeStep(ID, TITLE);
        RecipeStepDTO recipeStepDTO = createRecipeStepDTO(ID, TITLE);

        when(recipeStepRepository.findById(ID)).thenReturn(Optional.of(recipeStep));
        when(convertToDTO.convert(recipeStep)).thenReturn(recipeStepDTO);

        RecipeStepDTO result = recipeStepService.getById(ID);

        assertEquals(recipeStepDTO, result);
    }

    @DisplayName("Test getting a recipe step by id when the recipe step does not exist")
    @Test
    public void testGetRecipeStep_Failed() {
        when(recipeStepRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> recipeStepService.getById(ID));
    }

    @DisplayName("Test getting all recipe steps")
    @Test
    public void testGetAllRecipeSteps() {
        RecipeStep recipeStep1 = createRecipeStep(ID, TITLE);
        RecipeStep recipeStep2 = createRecipeStep(2L, TITLE);
        List<RecipeStep> recipeStepList = Arrays.asList(recipeStep1, recipeStep2);

        RecipeStepDTO recipeStepDTO1 = createRecipeStepDTO(ID, TITLE);
        RecipeStepDTO recipeStepDTO2 = createRecipeStepDTO(2L, TITLE);
        List<RecipeStepDTO> recipeStepDTOList = Arrays.asList(recipeStepDTO1, recipeStepDTO2);

        when(recipeStepRepository.findAll()).thenReturn(recipeStepList);
        when(convertToDTO.convert(recipeStep1)).thenReturn(recipeStepDTO1);
        when(convertToDTO.convert(recipeStep2)).thenReturn(recipeStepDTO2);

        List<RecipeStepDTO> result = recipeStepService.getAll();

        assertEquals(recipeStepDTOList, result);
    }

    @DisplayName("Test updating a recipe step when the recipe step exists")
    @Test
    public void testUpdateRecipeStep_Successful() {
        RecipeStepDTO updatedRecipeStepDTO = createRecipeStepDTO(ID, UPDATED_TITLE);
        RecipeStep existingRecipeStep = createRecipeStep(ID, TITLE);
        RecipeStep updatedRecipeStep = createRecipeStep(ID, TITLE);

        when(recipeStepRepository.findById(ID)).thenReturn(Optional.of(existingRecipeStep));
        when(convertToEntity.convert(updatedRecipeStepDTO)).thenReturn(updatedRecipeStep);
        when(recipeStepRepository.save(existingRecipeStep)).thenReturn(updatedRecipeStep);
        when(convertToDTO.convert(updatedRecipeStep)).thenReturn(updatedRecipeStepDTO);

        RecipeStepDTO result = recipeStepService.update(ID, updatedRecipeStepDTO);

        assertEquals(updatedRecipeStepDTO, result);
        verify(recipeStepRepository).save(existingRecipeStep);
    }

    @DisplayName("Test updating a recipe step when the recipe step does not exist")
    @Test
    public void testUpdateRecipeStep_Failed() {
        RecipeStepDTO recipeStepDTO = createRecipeStepDTO(ID, TITLE);

        when(recipeStepRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> recipeStepService.update(ID, recipeStepDTO));
    }

    @DisplayName("Test deleting a recipe step when the recipe step exists")
    @Test
    public void testDeleteRecipeStep_Successful() {
        when(recipeStepRepository.existsById(ID)).thenReturn(true);

        recipeStepService.delete(ID);

        verify(recipeStepRepository).deleteById(ID);
    }

    @DisplayName("Test deleting a recipe step when the recipe step does not exist")
    @Test
    public void testDeleteRecipeStep_Failed() {
        when(recipeStepRepository.existsById(ID)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> recipeStepService.delete(ID));
    }

    @DisplayName("Test creating a recipe step with null data")
    @Test
    public void testCreateRecipeStep_NullRecipeStepDTO() {
        assertThrows(IllegalArgumentException.class, () -> recipeStepService.create(null));
    }

    @DisplayName("Test updating a recipe step with null data")
    @Test
    public void testUpdateRecipeStep_NullRecipeStepDTO() {
        assertThrows(IllegalArgumentException.class, () -> recipeStepService.update(ID, null));
    }

    @DisplayName("Test updating a recipe step with null id")
    @Test
    public void testUpdateRecipeStep_NullId() {
        RecipeStepDTO recipeStepDTO = createRecipeStepDTO(ID, TITLE);
        assertThrows(IllegalArgumentException.class, () -> recipeStepService.update(null, recipeStepDTO));
    }

    @DisplayName("Test deleting a recipe step with null id")
    @Test
    public void testDeleteRecipeStep_NullId() {
        assertThrows(IllegalArgumentException.class, () -> recipeStepService.delete(null));
    }

    @DisplayName("Test getting a recipe step with null id")
    @Test
    public void testGetRecipeStep_NullId() {
        assertThrows(IllegalArgumentException.class, () -> recipeStepService.getById(null));
    }

    @DisplayName("Test deleting a recipe step with an invalid id")
    @Test
    public void testDeleteRecipeStepWithInvalidId() {
        assertThrows(NotFoundException.class, () -> recipeStepService.delete(INVALID_ID));
    }

    private RecipeStep createRecipeStep(Long id, String title) {
        RecipeStep recipeStep = new RecipeStep();
        recipeStep.setId(id);
        recipeStep.setNumber((short) 1);
        recipeStep.setTitle(title);
        recipeStep.setDescription("Test Description");
        recipeStep.setImage("Test Image");
        recipeStep.setRecipe(new Recipe());
        return recipeStep;
    }

    private RecipeStepDTO createRecipeStepDTO(Long id, String title) {
        return RecipeStepDTO.builder()
                .id(id)
                .number((short) 1)
                .title(title)
                .description("Test Description")
                .image("Test Image")
                .recipeId(1L)
                .build();
    }
}
