package com.epam.ecobites.service;

import com.epam.ecobites.converter.ShoppingItemConverter;
import com.epam.ecobites.data.ShoppingItemRepository;
import com.epam.ecobites.domain.ShoppingItem;
import com.epam.ecobites.dto.ShoppingItemDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingItemService implements CrudService<ShoppingItemDTO, Long> {
    private ShoppingItemRepository shoppingItemRepository;
    private ShoppingItemConverter shoppingItemConverter;

    public ShoppingItemService(ShoppingItemRepository shoppingItemRepository, ShoppingItemConverter shoppingItemConverter) {
        this.shoppingItemRepository = shoppingItemRepository;
        this.shoppingItemConverter = shoppingItemConverter;
    }

    @Override
    public ShoppingItemDTO create(ShoppingItemDTO shoppingItemDto) {
        ShoppingItem shoppingItem = shoppingItemConverter.convertToEntity(shoppingItemDto);
        ShoppingItem savedShoppingItem = shoppingItemRepository.save(shoppingItem);
        return shoppingItemConverter.convertToDTO(savedShoppingItem);
    }

    @Override
    public ShoppingItemDTO get(Long id) {
        ShoppingItem shoppingItem = shoppingItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ShoppingItem with id " + id + " not found"));
        return shoppingItemConverter.convertToDTO(shoppingItem);
    }

    @Override
    public List<ShoppingItemDTO> getAll() {
        List<ShoppingItem> shoppingItems = shoppingItemRepository.findAll();
        return shoppingItems.stream().map(shoppingItemConverter::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public ShoppingItemDTO update(Long id, ShoppingItemDTO updatedShoppingItemDto) {
        validateIds(id, updatedShoppingItemDto);
        ShoppingItem existingShoppingItem = findShoppingItemById(id);
        ShoppingItem updatedShoppingItem = shoppingItemConverter.convertToEntity(updatedShoppingItemDto);
        updateShoppingItemFields(existingShoppingItem, updatedShoppingItem);
        ShoppingItem savedShoppingItem = shoppingItemRepository.save(existingShoppingItem);
        return shoppingItemConverter.convertToDTO(savedShoppingItem);
    }

    private void validateIds(Long id, ShoppingItemDTO updatedShoppingItemDto) {
        if (!id.equals(updatedShoppingItemDto.getId())) {
            throw new IllegalArgumentException("ID in the path and ID in the ShoppingItem object must be the same");
        }
    }

    private ShoppingItem findShoppingItemById(Long id) {
        return shoppingItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ShoppingItem with id " + id + " not found"));
    }

    private void updateShoppingItemFields(ShoppingItem existingShoppingItem, ShoppingItem updatedShoppingItem) {
        existingShoppingItem.setRecipe(updatedShoppingItem.getRecipe());
        existingShoppingItem.setIngredient(updatedShoppingItem.getIngredient());
        existingShoppingItem.setEcoUser(updatedShoppingItem.getEcoUser());
    }

    @Override
    public void delete(Long id) {
        if (!shoppingItemRepository.existsById(id)) {
            throw new NotFoundException("ShoppingItem with id " + id + " not found");
        }
        shoppingItemRepository.deleteById(id);
    }
}
