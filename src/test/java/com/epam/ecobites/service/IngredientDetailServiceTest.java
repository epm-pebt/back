package com.epam.ecobites.service;

import com.epam.ecobites.converter.IngredientConvertToDTO;
import com.epam.ecobites.converter.IngredientConvertToEntity;
import com.epam.ecobites.converter.IngredientDetailConvertToDTO;
import com.epam.ecobites.converter.IngredientDetailConvertToEntity;
import com.epam.ecobites.data.IngredientDetailRepository;
import com.epam.ecobites.domain.IngredientDetail;
import com.epam.ecobites.dto.IngredientDetailDTO;
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
public class IngredientDetailServiceTest {

    @Mock
    private IngredientDetailRepository ingredientDetailRepository;

    @Mock
    private IngredientDetailConvertToDTO convertToDTO;

    @Mock
    private IngredientDetailConvertToEntity convertToEntity;

    @InjectMocks
    private IngredientDetailService ingredientDetailService;

    private static final Long ID = 1L;
    private static final Long INVALID_ID = -1L;
    private static final String UNIT = "test";
    private static final String UPDATED_UNIT = "testOther";

    @DisplayName("Test creating an ingredient detail with valid data")
    @Test
    public void testCreateIngredientDetail() {
        IngredientDetailDTO ingredientDetailDTO = createIngredientDetailDTO(ID, UNIT);
        IngredientDetail ingredientDetail = createIngredientDetail(ID, UNIT);

        when(convertToEntity.convert(ingredientDetailDTO)).thenReturn(ingredientDetail);
        when(ingredientDetailRepository.save(ingredientDetail)).thenReturn(ingredientDetail);
        when(convertToDTO.convert(ingredientDetail)).thenReturn(ingredientDetailDTO);

        IngredientDetailDTO result = ingredientDetailService.create(ingredientDetailDTO);

        assertEquals(ingredientDetailDTO, result);
        verify(ingredientDetailRepository).save(ingredientDetail);
    }

    @DisplayName("Test getting an ingredient detail by id when the ingredient detail exists")
    @Test
    public void testGetIngredientDetail_Successful() {
        IngredientDetail ingredientDetail = createIngredientDetail(ID, UNIT);
        IngredientDetailDTO ingredientDetailDTO = createIngredientDetailDTO(ID, UNIT);

        when(ingredientDetailRepository.findById(ID)).thenReturn(Optional.of(ingredientDetail));
        when(convertToDTO.convert(ingredientDetail)).thenReturn(ingredientDetailDTO);

        IngredientDetailDTO result = ingredientDetailService.getById(ID);

        assertEquals(ingredientDetailDTO, result);
    }

    @DisplayName("Test getting an ingredient detail by id when the ingredient detail does not exist")
    @Test
    public void testGetIngredientDetail_Failed() {
        when(ingredientDetailRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> ingredientDetailService.getById(ID));
    }

    @DisplayName("Test getting all ingredient details")
    @Test
    public void testGetAllIngredientDetails() {
        IngredientDetail ingredientDetail1 = createIngredientDetail(ID, UNIT);
        IngredientDetail ingredientDetail2 = createIngredientDetail(2L, UNIT);
        List<IngredientDetail> ingredientDetailList = Arrays.asList(ingredientDetail1, ingredientDetail2);

        IngredientDetailDTO ingredientDetailDTO1 = createIngredientDetailDTO(ID, UNIT);
        IngredientDetailDTO ingredientDetailDTO2 = createIngredientDetailDTO(2L, UNIT);
        List<IngredientDetailDTO> ingredientDetailDTOList = Arrays.asList(ingredientDetailDTO1, ingredientDetailDTO2);

        when(ingredientDetailRepository.findAll()).thenReturn(ingredientDetailList);
        when(convertToDTO.convert(ingredientDetail1)).thenReturn(ingredientDetailDTO1);
        when(convertToDTO.convert(ingredientDetail2)).thenReturn(ingredientDetailDTO2);

        List<IngredientDetailDTO> result = ingredientDetailService.getAll();

        assertEquals(ingredientDetailDTOList, result);
    }

    @DisplayName("Test updating an ingredient detail when the ingredient detail exists")
    @Test
    public void testUpdateIngredientDetail_Successful() {
        IngredientDetailDTO updatedIngredientDetailDTO = createIngredientDetailDTO(ID, UPDATED_UNIT);
        IngredientDetail existingIngredientDetail = createIngredientDetail(ID, UNIT);
        IngredientDetail updatedIngredientDetail = createIngredientDetail(ID, UPDATED_UNIT);

        when(ingredientDetailRepository.findById(ID)).thenReturn(Optional.of(existingIngredientDetail));
        when(convertToEntity.convert(updatedIngredientDetailDTO)).thenReturn(updatedIngredientDetail);
        when(ingredientDetailRepository.save(existingIngredientDetail)).thenReturn(updatedIngredientDetail);
        when(convertToDTO.convert(updatedIngredientDetail)).thenReturn(updatedIngredientDetailDTO);

        IngredientDetailDTO result = ingredientDetailService.update(ID, updatedIngredientDetailDTO);

        assertEquals(updatedIngredientDetailDTO, result);
        verify(ingredientDetailRepository).save(existingIngredientDetail);
    }

    @DisplayName("Test updating an ingredient detail when the ingredient detail does not exist")
    @Test
    public void testUpdateIngredientDetail_Failed() {
        IngredientDetailDTO ingredientDetailDTO = createIngredientDetailDTO(ID, UNIT);

        when(ingredientDetailRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> ingredientDetailService.update(ID, ingredientDetailDTO));
    }

    @DisplayName("Test deleting an ingredient detail when the ingredient detail exists")
    @Test
    public void testDeleteIngredientDetail_Successful() {
        when(ingredientDetailRepository.existsById(ID)).thenReturn(true);

        ingredientDetailService.delete(ID);

        verify(ingredientDetailRepository).deleteById(ID);
    }

    @DisplayName("Test deleting an ingredient detail when the ingredient detail does not exist")
    @Test
    public void testDeleteIngredientDetail_Failed() {
        when(ingredientDetailRepository.existsById(ID)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> ingredientDetailService.delete(ID));
    }

    @DisplayName("Test creating an ingredient detail with null data")
    @Test
    public void testCreateIngredientDetail_NullIngredientDetailDTO() {
        assertThrows(IllegalArgumentException.class, () -> ingredientDetailService.create(null));
    }

    @DisplayName("Test updating an ingredient detail with null data")
    @Test
    public void testUpdateIngredientDetail_NullIngredientDetailDTO() {
        assertThrows(IllegalArgumentException.class, () -> ingredientDetailService.update(ID, null));
    }

    @DisplayName("Test updating an ingredient detail with null id")
    @Test
    public void testUpdateIngredientDetail_NullId() {
        IngredientDetailDTO ingredientDetailDTO = createIngredientDetailDTO(ID, UNIT);
        assertThrows(IllegalArgumentException.class, () -> ingredientDetailService.update(null, ingredientDetailDTO));
    }

    @DisplayName("Test deleting an ingredient detail with null id")
    @Test
    public void testDeleteIngredientDetail_NullId() {
        assertThrows(IllegalArgumentException.class, () -> ingredientDetailService.delete(null));
    }

    @DisplayName("Test getting an ingredient detail with null id")
    @Test
    public void testGetIngredientDetail_NullId() {
        assertThrows(IllegalArgumentException.class, () -> ingredientDetailService.getById(null));
    }

    @DisplayName("Test deleting an ingredient detail with an invalid id")
    @Test
    public void testDeleteIngredientDetailWithInvalidId() {
        assertThrows(NotFoundException.class, () -> ingredientDetailService.delete(INVALID_ID));
    }

    private IngredientDetail createIngredientDetail(Long id, String unit) {
        IngredientDetail ingredientDetail = new IngredientDetail();
        ingredientDetail.setId(id);
        ingredientDetail.setQuantity(1);
        ingredientDetail.setUnit(unit);
        return ingredientDetail;
    }

    private IngredientDetailDTO createIngredientDetailDTO(Long id, String unit) {
        return IngredientDetailDTO.builder()
                .id(id)
                .quantity(1)
                .unit(unit)
                .build();
    }
}
