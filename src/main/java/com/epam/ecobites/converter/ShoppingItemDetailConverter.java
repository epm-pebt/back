package com.epam.ecobites.converter;

import com.epam.ecobites.data.ShoppingItemRepository;
import com.epam.ecobites.domain.ShoppingItem;
import com.epam.ecobites.domain.ShoppingItemDetail;
import com.epam.ecobites.dto.ShoppingItemDetailDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ShoppingItemDetailConverter implements EntityConverter<ShoppingItemDetailDTO, ShoppingItemDetail> {
    private ShoppingItemRepository shoppingItemRepository;

    public ShoppingItemDetailConverter(ShoppingItemRepository shoppingItemRepository) {
        this.shoppingItemRepository = shoppingItemRepository;
    }

    @Override
    public ShoppingItemDetailDTO toDTO(ShoppingItemDetail shoppingItemDetail) {
        return ShoppingItemDetailDTO.builder()
                .id(shoppingItemDetail.getId())
                .quantity(shoppingItemDetail.getQuantity())
                .unit(shoppingItemDetail.getUnit())
                .shoppingItemId(shoppingItemDetail.getShoppingItem().getId())
                .build();
    }

    @Override
    public ShoppingItemDetail fromDTO(ShoppingItemDetailDTO shoppingItemDetailDto) {
        ShoppingItem shoppingItem = shoppingItemRepository.findById(shoppingItemDetailDto.getShoppingItemId())
                .orElseThrow(() -> new NotFoundException("ShoppingItem with id " + shoppingItemDetailDto.getShoppingItemId() + " not found"));

        ShoppingItemDetail shoppingItemDetail = new ShoppingItemDetail();
        shoppingItemDetail.setId(shoppingItemDetailDto.getId());
        shoppingItemDetail.setQuantity(shoppingItemDetailDto.getQuantity());
        shoppingItemDetail.setUnit(shoppingItemDetailDto.getUnit());
        shoppingItemDetail.setShoppingItem(shoppingItem);
        return shoppingItemDetail;
    }
}
