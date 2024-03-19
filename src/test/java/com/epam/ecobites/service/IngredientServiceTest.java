package com.epam.ecobites.service;

import com.epam.ecobites.converter.IngredientConvertToDTO;
import com.epam.ecobites.converter.IngredientConvertToEntity;
import com.epam.ecobites.data.IngredientRepository;
import com.epam.ecobites.domain.Ingredient;
import com.epam.ecobites.dto.IngredientDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
public class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientConvertToDTO convertToDTO;

    @Mock
    private IngredientConvertToEntity convertToEntity;

    @InjectMocks
    private IngredientService ingredientService;

    private static final Long ID = 1L;
    private static final Long INVALID_ID = -1L;
    private static final String NAME = "test";
    private static final String UPDATED_NAME = "testOther";

    @DisplayName("Test creating an ingredient with valid data")
    @Test
    public void testCreateIngredient() {
        IngredientDTO ingredientDTO = createIngredientDTO(ID,NAME);
        Ingredient ingredient = createIngredient(ID,NAME);

        when(convertToEntity.convert(ingredientDTO)).thenReturn(ingredient);
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
        when(convertToDTO.convert(ingredient)).thenReturn(ingredientDTO);

        IngredientDTO result = ingredientService.create(ingredientDTO);

        assertEquals(ingredientDTO, result);
        verify(ingredientRepository).save(ingredient);
    }

    @DisplayName("Test getting an ingredient by id when the ingredient exists")
    @Test
    public void testGetIngredient_Successful() {
        Ingredient ingredient = createIngredient(ID,NAME);
        IngredientDTO ingredientDTO = createIngredientDTO(ID,NAME);

        when(ingredientRepository.findById(ID)).thenReturn(Optional.of(ingredient));
        when(convertToDTO.convert(ingredient)).thenReturn(ingredientDTO);

        IngredientDTO result = ingredientService.getById(ID);

        assertEquals(ingredientDTO, result);
    }

    @DisplayName("Test getting an ingredient by id when the ingredient does not exist")
    @Test
    public void testGetIngredient_Failed() {
        when(ingredientRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> ingredientService.getById(ID));
    }

    @DisplayName("Test getting all ingredients")
    @Test
    public void testGetAllIngredients() {
        Ingredient ingredient1 = createIngredient(ID,NAME);
        Ingredient ingredient2 = createIngredient(2L,NAME);
        List<Ingredient> ingredientList = Arrays.asList(ingredient1, ingredient2);

        IngredientDTO ingredientDTO1 = createIngredientDTO(ID,NAME);
        IngredientDTO ingredientDTO2 = createIngredientDTO(2L,NAME);
        List<IngredientDTO> ingredientDTOList = Arrays.asList(ingredientDTO1, ingredientDTO2);

        when(ingredientRepository.findAll()).thenReturn(ingredientList);
        when(convertToDTO.convert(ingredient1)).thenReturn(ingredientDTO1);
        when(convertToDTO.convert(ingredient2)).thenReturn(ingredientDTO2);

        List<IngredientDTO> result = ingredientService.getAll();

        assertEquals(ingredientDTOList, result);
    }

    @DisplayName("Test updating an ingredient when the ingredient exists")
    @Test
    public void testUpdateIngredient_Successful() {
        IngredientDTO updatedIngredientDTO = createIngredientDTO(ID,UPDATED_NAME);
        Ingredient existingIngredient = createIngredient(ID,NAME);
        Ingredient updatedIngredient = createIngredient(ID,UPDATED_NAME);

        when(ingredientRepository.findById(ID)).thenReturn(Optional.of(existingIngredient));
        when(convertToEntity.convert(updatedIngredientDTO)).thenReturn(updatedIngredient);
        when(ingredientRepository.save(existingIngredient)).thenReturn(updatedIngredient);
        when(convertToDTO.convert(updatedIngredient)).thenReturn(updatedIngredientDTO);

        IngredientDTO result = ingredientService.update(ID, updatedIngredientDTO);

        assertEquals(updatedIngredientDTO, result);
        verify(ingredientRepository).save(existingIngredient);
    }

    @DisplayName("Test updating an ingredient when the ingredient does not exist")
    @Test
    public void testUpdateIngredient_Failed() {
        IngredientDTO ingredientDTO = createIngredientDTO(ID,NAME);

        when(ingredientRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> ingredientService.update(ID, ingredientDTO));
    }

    @DisplayName("Test deleting an ingredient when the ingredient exists")
    @Test
    public void testDeleteIngredient_Successful() {
        when(ingredientRepository.existsById(ID)).thenReturn(true);

        ingredientService.delete(ID);

        verify(ingredientRepository).deleteById(ID);
    }

    @DisplayName("Test deleting an ingredient when the ingredient does not exist")
    @Test
    public void testDeleteIngredient_Failed() {
        when(ingredientRepository.existsById(ID)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> ingredientService.delete(ID));
    }

    @DisplayName("Test creating an ingredient with null data")
    @Test
    public void testCreateIngredient_NullIngredientDTO() {
        assertThrows(IllegalArgumentException.class, () -> ingredientService.create(null));
    }

    @DisplayName("Test updating an ingredient with null data")
    @Test
    public void testUpdateIngredient_NullIngredientDTO() {
        assertThrows(IllegalArgumentException.class, () -> ingredientService.update(ID, null));
    }

    @DisplayName("Test updating an ingredient with null id")
    @Test
    public void testUpdateIngredient_NullId() {
        IngredientDTO ingredientDTO = createIngredientDTO(ID,NAME);
        assertThrows(IllegalArgumentException.class, () -> ingredientService.update(null, ingredientDTO));
    }

    @DisplayName("Test deleting an ingredient with null id")
    @Test
    public void testDeleteIngredient_NullId() {
        assertThrows(IllegalArgumentException.class, () -> ingredientService.delete(null));
    }

    @DisplayName("Test getting an ingredient with null id")
    @Test
    public void testGetIngredient_NullId() {
        assertThrows(IllegalArgumentException.class, () -> ingredientService.getById(null));
    }

    @DisplayName("Test deleting an ingredient with an invalid id")
    @Test
    public void testDeleteIngredientWithInvalidId() {
        assertThrows(NotFoundException.class, () -> ingredientService.delete(INVALID_ID));
    }

    private Ingredient createIngredient(Long id, String name) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        ingredient.setName(name);
        return ingredient;
    }

    private IngredientDTO createIngredientDTO(Long id, String name) {
        return IngredientDTO.builder()
                .id(id)
                .name(name)
                .build();
    }
}
