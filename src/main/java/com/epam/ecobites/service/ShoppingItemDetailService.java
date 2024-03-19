package com.epam.ecobites.service;

import com.epam.ecobites.converter.ShoppingItemDetailConvertToDTO;
import com.epam.ecobites.converter.ShoppingItemDetailConvertToEntity;
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
    private ShoppingItemDetailConvertToDTO convertToDTO;
    private ShoppingItemDetailConvertToEntity convertToEntity;

    public ShoppingItemDetailService(ShoppingItemDetailRepository shoppingItemDetailRepository, ShoppingItemDetailConvertToDTO convertToDTO, ShoppingItemDetailConvertToEntity convertToEntity) {
        this.shoppingItemDetailRepository = shoppingItemDetailRepository;
        this.convertToDTO = convertToDTO;
        this.convertToEntity = convertToEntity;
    }

    @Override
    public ShoppingItemDetailDTO create(ShoppingItemDetailDTO shoppingItemDetailDto) {
        nullCheck(shoppingItemDetailDto);
        ShoppingItemDetail shoppingItemDetail = convertToEntity.convert(shoppingItemDetailDto);
        ShoppingItemDetail savedShoppingItemDetail = shoppingItemDetailRepository.save(shoppingItemDetail);
        return convertToDTO.convert(savedShoppingItemDetail);
    }

    @Override
    public ShoppingItemDetailDTO getById(Long id) {
        nullCheck(id);
        ShoppingItemDetail shoppingItemDetail = shoppingItemDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ShoppingItemDetail with id " + id + " not found"));
        return convertToDTO.convert(shoppingItemDetail);
    }

    @Override
    public List<ShoppingItemDetailDTO> getAll() {
        List<ShoppingItemDetail> shoppingItemDetails = shoppingItemDetailRepository.findAll();
        return shoppingItemDetails.stream().map(convertToDTO::convert).collect(Collectors.toList());
    }

    @Override
    public ShoppingItemDetailDTO update(Long id, ShoppingItemDetailDTO updatedShoppingItemDetailDto) {
        nullCheck(id);
        nullCheck(updatedShoppingItemDetailDto);
        validateIds(id, updatedShoppingItemDetailDto);
        ShoppingItemDetail existingShoppingItemDetail = findShoppingItemDetailById(id);
        ShoppingItemDetail updatedShoppingItemDetail = convertToEntity.convert(updatedShoppingItemDetailDto);
        updateShoppingItemDetailFields(existingShoppingItemDetail, updatedShoppingItemDetail);
        ShoppingItemDetail savedShoppingItemDetail = shoppingItemDetailRepository.save(existingShoppingItemDetail);
        return convertToDTO.convert(savedShoppingItemDetail);
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
        nullCheck(id);
        if (!shoppingItemDetailRepository.existsById(id)) {
            throw new NotFoundException("ShoppingItemDetail with id " + id + " not found");
        }
        shoppingItemDetailRepository.deleteById(id);
    }

    private void nullCheck(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
    }
}
