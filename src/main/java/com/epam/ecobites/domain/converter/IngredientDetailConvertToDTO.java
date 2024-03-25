package com.epam.ecobites.domain.converter;

import com.epam.ecobites.domain.IngredientDetail;
import com.epam.ecobites.domain.dto.IngredientDetailDTO;
import org.springframework.core.convert.converter.Converter;

public class IngredientDetailConvertToDTO implements Converter<IngredientDetail, IngredientDetailDTO> {
    @Override
    public IngredientDetailDTO convert(IngredientDetail source) {
        return IngredientDetailDTO.builder()
                .id(source.getId())
                .quantity(source.getQuantity())
                .unit(source.getUnit())
                .build();
    }
}
