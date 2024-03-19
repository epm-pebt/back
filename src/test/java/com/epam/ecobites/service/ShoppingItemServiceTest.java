package com.epam.ecobites.service;

import com.epam.ecobites.converter.ShoppingItemConvertToDTO;
import com.epam.ecobites.converter.ShoppingItemConvertToEntity;
import com.epam.ecobites.data.ShoppingItemRepository;
import com.epam.ecobites.domain.EcoUser;
import com.epam.ecobites.domain.Ingredient;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.ShoppingItem;
import com.epam.ecobites.dto.ShoppingItemDTO;
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
public class ShoppingItemServiceTest {

    @Mock
    private ShoppingItemRepository shoppingItemRepository;

    @Mock
    private ShoppingItemConvertToDTO convertToDTO;

    @Mock
    private ShoppingItemConvertToEntity convertToEntity;

    @InjectMocks
    private ShoppingItemService shoppingItemService;

    private static final Long ID = 1L;
    private static final Long INVALID_ID = -1L;
    private static final Recipe RECIPE = new Recipe();
    private static final Recipe UPDATED_RECIPE = new Recipe();
    private static final Long RECIPE_ID = 2L;

    @DisplayName("Test creating a shopping item with valid data")
    @Test
    public void testCreateShoppingItem() {
        ShoppingItemDTO shoppingItemDTO = createShoppingItemDTO(ID, RECIPE_ID);
        ShoppingItem shoppingItem = createShoppingItem(ID, RECIPE);

        when(convertToEntity.convert(shoppingItemDTO)).thenReturn(shoppingItem);
        when(shoppingItemRepository.save(shoppingItem)).thenReturn(shoppingItem);
        when(convertToDTO.convert(shoppingItem)).thenReturn(shoppingItemDTO);

        ShoppingItemDTO result = shoppingItemService.create(shoppingItemDTO);

        assertEquals(shoppingItemDTO, result);
        verify(shoppingItemRepository).save(shoppingItem);
    }

    @DisplayName("Test getting a shopping item by id when the shopping item exists")
    @Test
    public void testGetShoppingItem_Successful() {
        ShoppingItem shoppingItem = createShoppingItem(ID, RECIPE);
        ShoppingItemDTO shoppingItemDTO = createShoppingItemDTO(ID, RECIPE_ID);

        when(shoppingItemRepository.findById(ID)).thenReturn(Optional.of(shoppingItem));
        when(convertToDTO.convert(shoppingItem)).thenReturn(shoppingItemDTO);

        ShoppingItemDTO result = shoppingItemService.getById(ID);

        assertEquals(shoppingItemDTO, result);
    }

    @DisplayName("Test getting a shopping item by id when the shopping item does not exist")
    @Test
    public void testGetShoppingItem_Failed() {
        when(shoppingItemRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> shoppingItemService.getById(ID));
    }

    @DisplayName("Test getting all shopping items")
    @Test
    public void testGetAllShoppingItems() {
        ShoppingItem shoppingItem1 = createShoppingItem(ID, RECIPE);
        ShoppingItem shoppingItem2 = createShoppingItem(2L, RECIPE);
        List<ShoppingItem> shoppingItemList = Arrays.asList(shoppingItem1, shoppingItem2);

        ShoppingItemDTO shoppingItemDTO1 = createShoppingItemDTO(ID, RECIPE_ID);
        ShoppingItemDTO shoppingItemDTO2 = createShoppingItemDTO(2L, RECIPE_ID);
        List<ShoppingItemDTO> shoppingItemDTOList = Arrays.asList(shoppingItemDTO1, shoppingItemDTO2);

        when(shoppingItemRepository.findAll()).thenReturn(shoppingItemList);
        when(convertToDTO.convert(shoppingItem1)).thenReturn(shoppingItemDTO1);
        when(convertToDTO.convert(shoppingItem2)).thenReturn(shoppingItemDTO2);

        List<ShoppingItemDTO> result = shoppingItemService.getAll();

        assertEquals(shoppingItemDTOList, result);
    }

    @DisplayName("Test updating a shopping item when the shopping item exists")
    @Test
    public void testUpdateShoppingItem_Successful() {
        ShoppingItemDTO updatedShoppingItemDTO = createShoppingItemDTO(ID, RECIPE_ID);
        ShoppingItem existingShoppingItem = createShoppingItem(ID, RECIPE);
        ShoppingItem updatedShoppingItem = createShoppingItem(ID, UPDATED_RECIPE);

        when(shoppingItemRepository.findById(ID)).thenReturn(Optional.of(existingShoppingItem));
        when(convertToEntity.convert(updatedShoppingItemDTO)).thenReturn(updatedShoppingItem);
        when(shoppingItemRepository.save(existingShoppingItem)).thenReturn(updatedShoppingItem);
        when(convertToDTO.convert(updatedShoppingItem)).thenReturn(updatedShoppingItemDTO);

        ShoppingItemDTO result = shoppingItemService.update(ID, updatedShoppingItemDTO);

        assertEquals(updatedShoppingItemDTO, result);
        verify(shoppingItemRepository).save(existingShoppingItem);
    }

    @DisplayName("Test updating a shopping item when the shopping item does not exist")
    @Test
    public void testUpdateShoppingItem_Failed() {
        ShoppingItemDTO shoppingItemDTO = createShoppingItemDTO(ID, RECIPE_ID);

        when(shoppingItemRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> shoppingItemService.update(ID, shoppingItemDTO));
    }

    @DisplayName("Test deleting a shopping item when the shopping item exists")
    @Test
    public void testDeleteShoppingItem_Successful() {
        when(shoppingItemRepository.existsById(ID)).thenReturn(true);

        shoppingItemService.delete(ID);

        verify(shoppingItemRepository).deleteById(ID);
    }

    @DisplayName("Test deleting a shopping item when the shopping item does not exist")
    @Test
    public void testDeleteShoppingItem_Failed() {
        when(shoppingItemRepository.existsById(ID)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> shoppingItemService.delete(ID));
    }

    @DisplayName("Test deleting a shopping item with an invalid id")
    @Test
    public void testDeleteShoppingItemWithInvalidId() {
        assertThrows(NotFoundException.class, () -> shoppingItemService.delete(INVALID_ID));
    }

    @DisplayName("Test creating a shopping item with null data")
    @Test
    public void testCreateShoppingItem_NullShoppingItemDTO() {
        assertThrows(IllegalArgumentException.class, () -> shoppingItemService.create(null));
    }

    @DisplayName("Test updating a shopping item with null data")
    @Test
    public void testUpdateShoppingItem_NullShoppingItemDTO() {
        assertThrows(IllegalArgumentException.class, () -> shoppingItemService.update(ID, null));
    }

    @DisplayName("Test updating a shopping item with null id")
    @Test
    public void testUpdateShoppingItem_NullId() {
        ShoppingItemDTO shoppingItemDTO = createShoppingItemDTO(ID, RECIPE_ID);
        assertThrows(IllegalArgumentException.class, () -> shoppingItemService.update(null, shoppingItemDTO));
    }

    @DisplayName("Test deleting a shopping item with null id")
    @Test
    public void testDeleteShoppingItem_NullId() {
        assertThrows(IllegalArgumentException.class, () -> shoppingItemService.delete(null));
    }

    @DisplayName("Test getting a shopping item with null id")
    @Test
    public void testGetShoppingItem_NullId() {
        assertThrows(IllegalArgumentException.class, () -> shoppingItemService.getById(null));
    }

    private ShoppingItem createShoppingItem(Long id, Recipe recipe) {
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setId(id);
        shoppingItem.setRecipe(recipe);
        shoppingItem.setIngredient(new Ingredient());
        shoppingItem.setEcoUser(new EcoUser());
        return shoppingItem;
    }

    private ShoppingItemDTO createShoppingItemDTO(Long id, Long recipeId) {
        return ShoppingItemDTO.builder()
                .id(id)
                .recipeId(recipeId)
                .ingredientId(id)
                .ecoUserId(id)
                .build();
    }
}
