package com.epam.ecobites.converter;

import com.epam.ecobites.domain.ShoppingItemDetail;
import com.epam.ecobites.dto.ShoppingItemDetailDTO;
import org.springframework.core.convert.converter.Converter;

public class ShoppingItemDetailConvertToDTO implements Converter<ShoppingItemDetail, ShoppingItemDetailDTO> {
    @Override
    public ShoppingItemDetailDTO convert(ShoppingItemDetail source) {
        return ShoppingItemDetailDTO.builder()
                .id(source.getId())
                .quantity(source.getQuantity())
                .unit(source.getUnit())
                .shoppingItemId(source.getShoppingItem().getId())
                .build();
    }
}
