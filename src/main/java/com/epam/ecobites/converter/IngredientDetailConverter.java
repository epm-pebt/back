package com.epam.ecobites.converter;

import com.epam.ecobites.domain.IngredientDetail;
import com.epam.ecobites.dto.IngredientDetailDTO;

public class IngredientDetailConverter implements EntityConverter<IngredientDetailDTO, IngredientDetail> {
    @Override
    public IngredientDetailDTO convertToDTO(IngredientDetail ingredientDetail) {
        return IngredientDetailDTO.builder()
                .id(ingredientDetail.getId())
                .quantity(ingredientDetail.getQuantity())
                .unit(ingredientDetail.getUnit())
                .build();
    }

    @Override
    public IngredientDetail convertToEntity(IngredientDetailDTO ingredientDetailDto) {
        IngredientDetail ingredientDetail = new IngredientDetail();
        ingredientDetail.setId(ingredientDetailDto.getId());
        ingredientDetail.setQuantity(ingredientDetailDto.getQuantity());
        ingredientDetail.setUnit(ingredientDetailDto.getUnit());
        return ingredientDetail;
    }
}
