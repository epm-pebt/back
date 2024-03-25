package com.epam.ecobites.domain.converter;

import com.epam.ecobites.data.ShoppingItemRepository;
import com.epam.ecobites.domain.ShoppingItem;
import com.epam.ecobites.domain.ShoppingItemDetail;
import com.epam.ecobites.domain.dto.ShoppingItemDetailDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ShoppingItemDetailConvertToEntity implements Converter<ShoppingItemDetailDTO, ShoppingItemDetail> {
    private ShoppingItemRepository shoppingItemRepository;

    public ShoppingItemDetailConvertToEntity(ShoppingItemRepository shoppingItemRepository) {
        this.shoppingItemRepository = shoppingItemRepository;
    }

    @Override
    public ShoppingItemDetail convert(ShoppingItemDetailDTO source) {
        ShoppingItem shoppingItem = shoppingItemRepository.findById(source.getShoppingItemId())
                .orElseThrow(() -> new NotFoundException("ShoppingItem with id " + source.getShoppingItemId() + " not found"));

        ShoppingItemDetail shoppingItemDetail = new ShoppingItemDetail();
        shoppingItemDetail.setId(source.getId());
        shoppingItemDetail.setQuantity(source.getQuantity());
        shoppingItemDetail.setUnit(source.getUnit());
        shoppingItemDetail.setShoppingItem(shoppingItem);
        return shoppingItemDetail;
    }
}
