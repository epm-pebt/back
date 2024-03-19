package com.epam.ecobites.service;

import com.epam.ecobites.converter.ShoppingItemDetailConvertToDTO;
import com.epam.ecobites.converter.ShoppingItemDetailConvertToEntity;
import com.epam.ecobites.data.ShoppingItemDetailRepository;
import com.epam.ecobites.domain.ShoppingItem;
import com.epam.ecobites.domain.ShoppingItemDetail;
import com.epam.ecobites.dto.ShoppingItemDetailDTO;
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
public class ShoppingItemDetailServiceTest {

    @Mock
    private ShoppingItemDetailRepository shoppingItemDetailRepository;

    @Mock
    private ShoppingItemDetailConvertToDTO convertToDTO;

    @Mock
    private ShoppingItemDetailConvertToEntity convertToEntity;

    @InjectMocks
    private ShoppingItemDetailService shoppingItemDetailService;

    private static final Long ID = 1L;
    private static final Long INVALID_ID = -1L;
    private static final String UNIT = "test";
    private static final String UPDATED_UNIT = "testOther";

    @DisplayName("Test creating a shopping item detail with valid data")
    @Test
    public void testCreateShoppingItemDetail() {
        ShoppingItemDetailDTO shoppingItemDetailDTO = createShoppingItemDetailDTO(ID, UNIT);
        ShoppingItemDetail shoppingItemDetail = createShoppingItemDetail(ID, UNIT);

        when(convertToEntity.convert(shoppingItemDetailDTO)).thenReturn(shoppingItemDetail);
        when(shoppingItemDetailRepository.save(shoppingItemDetail)).thenReturn(shoppingItemDetail);
        when(convertToDTO.convert(shoppingItemDetail)).thenReturn(shoppingItemDetailDTO);

        ShoppingItemDetailDTO result = shoppingItemDetailService.create(shoppingItemDetailDTO);

        assertEquals(shoppingItemDetailDTO, result);
        verify(shoppingItemDetailRepository).save(shoppingItemDetail);
    }

    @DisplayName("Test getting a shopping item detail by id when the shopping item detail exists")
    @Test
    public void testGetShoppingItemDetail_Successful() {
        ShoppingItemDetail shoppingItemDetail = createShoppingItemDetail(ID, UNIT);
        ShoppingItemDetailDTO shoppingItemDetailDTO = createShoppingItemDetailDTO(ID, UNIT);

        when(shoppingItemDetailRepository.findById(ID)).thenReturn(Optional.of(shoppingItemDetail));
        when(convertToDTO.convert(shoppingItemDetail)).thenReturn(shoppingItemDetailDTO);

        ShoppingItemDetailDTO result = shoppingItemDetailService.getById(ID);

        assertEquals(shoppingItemDetailDTO, result);
    }

    @DisplayName("Test getting a shopping item detail by id when the shopping item detail does not exist")
    @Test
    public void testGetShoppingItemDetail_Failed() {
        when(shoppingItemDetailRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> shoppingItemDetailService.getById(ID));
    }

    @DisplayName("Test getting all shopping item details")
    @Test
    public void testGetAllShoppingItemDetails() {
        ShoppingItemDetail shoppingItemDetail1 = createShoppingItemDetail(ID, UNIT);
        ShoppingItemDetail shoppingItemDetail2 = createShoppingItemDetail(2L, UNIT);
        List<ShoppingItemDetail> shoppingItemDetailList = Arrays.asList(shoppingItemDetail1, shoppingItemDetail2);

        ShoppingItemDetailDTO shoppingItemDetailDTO1 = createShoppingItemDetailDTO(ID, UNIT);
        ShoppingItemDetailDTO shoppingItemDetailDTO2 = createShoppingItemDetailDTO(2L, UNIT);
        List<ShoppingItemDetailDTO> shoppingItemDetailDTOList = Arrays.asList(shoppingItemDetailDTO1, shoppingItemDetailDTO2);

        when(shoppingItemDetailRepository.findAll()).thenReturn(shoppingItemDetailList);
        when(convertToDTO.convert(shoppingItemDetail1)).thenReturn(shoppingItemDetailDTO1);
        when(convertToDTO.convert(shoppingItemDetail2)).thenReturn(shoppingItemDetailDTO2);

        List<ShoppingItemDetailDTO> result = shoppingItemDetailService.getAll();

        assertEquals(shoppingItemDetailDTOList, result);
    }

    @DisplayName("Test updating a shopping item detail when the shopping item detail exists")
    @Test
    public void testUpdateShoppingItemDetail_Successful() {
        ShoppingItemDetailDTO updatedShoppingItemDetailDTO = createShoppingItemDetailDTO(ID, UPDATED_UNIT);
        ShoppingItemDetail existingShoppingItemDetail = createShoppingItemDetail(ID, UNIT);
        ShoppingItemDetail updatedShoppingItemDetail = createShoppingItemDetail(ID, UPDATED_UNIT);

        when(shoppingItemDetailRepository.findById(ID)).thenReturn(Optional.of(existingShoppingItemDetail));
        when(convertToEntity.convert(updatedShoppingItemDetailDTO)).thenReturn(updatedShoppingItemDetail);
        when(shoppingItemDetailRepository.save(existingShoppingItemDetail)).thenReturn(updatedShoppingItemDetail);
        when(convertToDTO.convert(updatedShoppingItemDetail)).thenReturn(updatedShoppingItemDetailDTO);

        ShoppingItemDetailDTO result = shoppingItemDetailService.update(ID, updatedShoppingItemDetailDTO);

        assertEquals(updatedShoppingItemDetailDTO, result);
        verify(shoppingItemDetailRepository).save(existingShoppingItemDetail);
    }

    @DisplayName("Test updating a shopping item detail when the shopping item detail does not exist")
    @Test
    public void testUpdateShoppingItemDetail_Failed() {
        ShoppingItemDetailDTO shoppingItemDetailDTO = createShoppingItemDetailDTO(ID, UNIT);

        when(shoppingItemDetailRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> shoppingItemDetailService.update(ID, shoppingItemDetailDTO));
    }

    @DisplayName("Test deleting a shopping item detail when the shopping item detail exists")
    @Test
    public void testDeleteShoppingItemDetail_Successful() {
        when(shoppingItemDetailRepository.existsById(ID)).thenReturn(true);

        shoppingItemDetailService.delete(ID);

        verify(shoppingItemDetailRepository).deleteById(ID);
    }

    @DisplayName("Test deleting a shopping item detail when the shopping item detail does not exist")
    @Test
    public void testDeleteShoppingItemDetail_Failed() {
        when(shoppingItemDetailRepository.existsById(ID)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> shoppingItemDetailService.delete(ID));
    }

    @DisplayName("Test creating a shopping item detail with null data")
    @Test
    public void testCreateShoppingItemDetail_NullShoppingItemDetailDTO() {
        assertThrows(IllegalArgumentException.class, () -> shoppingItemDetailService.create(null));
    }

    @DisplayName("Test updating a shopping item detail with null data")
    @Test
    public void testUpdateShoppingItemDetail_NullShoppingItemDetailDTO() {
        assertThrows(IllegalArgumentException.class, () -> shoppingItemDetailService.update(ID, null));
    }

    @DisplayName("Test updating a shopping item detail with null id")
    @Test
    public void testUpdateShoppingItemDetail_NullId() {
        ShoppingItemDetailDTO shoppingItemDetailDTO = createShoppingItemDetailDTO(ID, UNIT);
        assertThrows(IllegalArgumentException.class, () -> shoppingItemDetailService.update(null, shoppingItemDetailDTO));
    }

    @DisplayName("Test deleting a shopping item detail with null id")
    @Test
    public void testDeleteShoppingItemDetail_NullId() {
        assertThrows(IllegalArgumentException.class, () -> shoppingItemDetailService.delete(null));
    }

    @DisplayName("Test deleting a shopping item detail with an invalid id")
    @Test
    public void testDeleteShoppingItemDetailWithInvalidId() {
        assertThrows(NotFoundException.class, () -> shoppingItemDetailService.delete(INVALID_ID));
    }

    @DisplayName("Test getting a shopping item detail with null id")
    @Test
    public void testGetShoppingItemDetail_NullId() {
        assertThrows(IllegalArgumentException.class, () -> shoppingItemDetailService.getById(null));
    }

    private ShoppingItemDetail createShoppingItemDetail(Long id, String unit) {
        ShoppingItemDetail shoppingItemDetail = new ShoppingItemDetail();
        shoppingItemDetail.setId(id);
        shoppingItemDetail.setQuantity(1);
        shoppingItemDetail.setUnit(unit);
        shoppingItemDetail.setShoppingItem(new ShoppingItem());
        return shoppingItemDetail;
    }

    private ShoppingItemDetailDTO createShoppingItemDetailDTO(Long id, String unit) {
        return ShoppingItemDetailDTO.builder()
                .id(id)
                .quantity(1)
                .unit(unit)
                .shoppingItemId(id)
                .build();
    }
}
