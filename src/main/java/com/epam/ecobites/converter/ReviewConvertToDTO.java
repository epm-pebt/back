package com.epam.ecobites.converter;

import com.epam.ecobites.domain.Review;
import com.epam.ecobites.dto.ReviewDTO;
import org.springframework.core.convert.converter.Converter;

public class ReviewConvertToDTO implements Converter<Review, ReviewDTO> {
    @Override
    public ReviewDTO convert(Review source) {
        return ReviewDTO.builder()
                .id(source.getId())
                .rate(source.getRate())
                .content(source.getContent())
                .dateCreated(source.getDateCreated())
                .ecoUserId(source.getEcoUser().getId())
                .recipeId(source.getRecipe().getId())
                .build();
    }
}
