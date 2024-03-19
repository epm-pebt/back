package com.epam.ecobites.converter;

import com.epam.ecobites.domain.IngredientDetail;
import com.epam.ecobites.dto.IngredientDetailDTO;
import org.springframework.core.convert.converter.Converter;

public class IngredientDetailConvertToEntity implements Converter<IngredientDetailDTO, IngredientDetail> {
    @Override
    public IngredientDetail convert(IngredientDetailDTO source) {
        IngredientDetail ingredientDetail = new IngredientDetail();
        ingredientDetail.setId(source.getId());
        ingredientDetail.setQuantity(source.getQuantity());
        ingredientDetail.setUnit(source.getUnit());
        return ingredientDetail;
    }
}
