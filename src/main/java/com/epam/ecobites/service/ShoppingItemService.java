package com.epam.ecobites.service;

import com.epam.ecobites.converter.ShoppingItemConvertToDTO;
import com.epam.ecobites.converter.ShoppingItemConvertToEntity;
import com.epam.ecobites.converter.ShoppingItemDetailConvertToEntity;
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
    private ShoppingItemConvertToDTO convertToDTO;
    private ShoppingItemConvertToEntity convertToEntity;

    public ShoppingItemService(ShoppingItemRepository shoppingItemRepository, ShoppingItemConvertToDTO convertToDTO, ShoppingItemConvertToEntity convertToEntity) {
        this.shoppingItemRepository = shoppingItemRepository;
        this.convertToDTO = convertToDTO;
        this.convertToEntity = convertToEntity;
    }

    @Override
    public ShoppingItemDTO create(ShoppingItemDTO shoppingItemDto) {
        nullCheck(shoppingItemDto);
        ShoppingItem shoppingItem = convertToEntity.convert(shoppingItemDto);
        ShoppingItem savedShoppingItem = shoppingItemRepository.save(shoppingItem);
        return convertToDTO.convert(savedShoppingItem);
    }

    @Override
    public ShoppingItemDTO getById(Long id) {
        nullCheck(id);
        ShoppingItem shoppingItem = shoppingItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ShoppingItem with id " + id + " not found"));
        return convertToDTO.convert(shoppingItem);
    }

    @Override
    public List<ShoppingItemDTO> getAll() {
        List<ShoppingItem> shoppingItems = shoppingItemRepository.findAll();
        return shoppingItems.stream().map(convertToDTO::convert).collect(Collectors.toList());
    }

    @Override
    public ShoppingItemDTO update(Long id, ShoppingItemDTO updatedShoppingItemDto) {
        nullCheck(id);
        nullCheck(updatedShoppingItemDto);
        validateIds(id, updatedShoppingItemDto);
        ShoppingItem existingShoppingItem = findShoppingItemById(id);
        ShoppingItem updatedShoppingItem = convertToEntity.convert(updatedShoppingItemDto);
        updateShoppingItemFields(existingShoppingItem, updatedShoppingItem);
        ShoppingItem savedShoppingItem = shoppingItemRepository.save(existingShoppingItem);
        return convertToDTO.convert(savedShoppingItem);
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
        nullCheck(id);
        if (!shoppingItemRepository.existsById(id)) {
            throw new NotFoundException("ShoppingItem with id " + id + " not found");
        }
        shoppingItemRepository.deleteById(id);
    }

    private void nullCheck(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
    }
}
