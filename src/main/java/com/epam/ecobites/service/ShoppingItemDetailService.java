package com.epam.ecobites.service;

import com.epam.ecobites.converter.ShoppingItemDetailConverter;
import com.epam.ecobites.data.ShoppingItemDetailRepository;
import com.epam.ecobites.domain.ShoppingItemDetail;
import com.epam.ecobites.dto.ShoppingItemDetailDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingItemDetailService implements CrudService<ShoppingItemDetailDTO, Long> {
    private ShoppingItemDetailRepository shoppingItemDetailRepository;
    private ShoppingItemDetailConverter shoppingItemDetailConverter;

    public ShoppingItemDetailService(ShoppingItemDetailRepository shoppingItemDetailRepository, ShoppingItemDetailConverter shoppingItemDetailConverter) {
        this.shoppingItemDetailRepository = shoppingItemDetailRepository;
        this.shoppingItemDetailConverter = shoppingItemDetailConverter;
    }

    @Override
    public ShoppingItemDetailDTO create(ShoppingItemDetailDTO shoppingItemDetailDto) {
        ShoppingItemDetail shoppingItemDetail = shoppingItemDetailConverter.fromDTO(shoppingItemDetailDto);
        ShoppingItemDetail savedShoppingItemDetail = shoppingItemDetailRepository.save(shoppingItemDetail);
        return shoppingItemDetailConverter.toDTO(savedShoppingItemDetail);
    }

    @Override
    public ShoppingItemDetailDTO get(Long id) {
        ShoppingItemDetail shoppingItemDetail = shoppingItemDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ShoppingItemDetail with id " + id + " not found"));
        return shoppingItemDetailConverter.toDTO(shoppingItemDetail);
    }

    @Override
    public List<ShoppingItemDetailDTO> getAll() {
        List<ShoppingItemDetail> shoppingItemDetails = shoppingItemDetailRepository.findAll();
        return shoppingItemDetails.stream().map(shoppingItemDetailConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public ShoppingItemDetailDTO update(Long id, ShoppingItemDetailDTO updatedShoppingItemDetailDto) {
        validateIds(id, updatedShoppingItemDetailDto);
        ShoppingItemDetail existingShoppingItemDetail = findShoppingItemDetailById(id);
        ShoppingItemDetail updatedShoppingItemDetail = shoppingItemDetailConverter.fromDTO(updatedShoppingItemDetailDto);
        updateShoppingItemDetailFields(existingShoppingItemDetail, updatedShoppingItemDetail);
        ShoppingItemDetail savedShoppingItemDetail = shoppingItemDetailRepository.save(existingShoppingItemDetail);
        return shoppingItemDetailConverter.toDTO(savedShoppingItemDetail);
    }

    private void validateIds(Long id, ShoppingItemDetailDTO updatedShoppingItemDetailDto) {
        if (!id.equals(updatedShoppingItemDetailDto.getId())) {
            throw new IllegalArgumentException("ID in the path and ID in the ShoppingItemDetail object must be the same");
        }
    }

    private ShoppingItemDetail findShoppingItemDetailById(Long id) {
        return shoppingItemDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ShoppingItemDetail with id " + id + " not found"));
    }

    private void updateShoppingItemDetailFields(ShoppingItemDetail existingShoppingItemDetail, ShoppingItemDetail updatedShoppingItemDetail) {
        existingShoppingItemDetail.setQuantity(updatedShoppingItemDetail.getQuantity());
        existingShoppingItemDetail.setUnit(updatedShoppingItemDetail.getUnit());
        existingShoppingItemDetail.setShoppingItem(updatedShoppingItemDetail.getShoppingItem());
    }

    @Override
    public void delete(Long id) {
        if (!shoppingItemDetailRepository.existsById(id)) {
            throw new NotFoundException("ShoppingItemDetail with id " + id + " not found");
        }
        shoppingItemDetailRepository.deleteById(id);
    }
}
