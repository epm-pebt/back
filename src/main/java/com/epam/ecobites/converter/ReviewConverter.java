package com.epam.ecobites.converter;

import com.epam.ecobites.data.EcoUserRepository;
import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.domain.EcoUser;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.Review;
import com.epam.ecobites.dto.ReviewDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverter implements EntityConverter<ReviewDTO, Review> {
    private EcoUserRepository ecoUserRepository;
    private RecipeRepository recipeRepository;

    public ReviewConverter(EcoUserRepository ecoUserRepository, RecipeRepository recipeRepository) {
        this.ecoUserRepository = ecoUserRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public ReviewDTO convertToDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .rate(review.getRate())
                .content(review.getContent())
                .dateCreated(review.getDateCreated())
                .ecoUserId(review.getEcoUser().getId())
                .recipeId(review.getRecipe().getId())
                .build();
    }

    @Override
    public Review convertToEntity(ReviewDTO reviewDto) {
        EcoUser ecoUser = ecoUserRepository.findById(reviewDto.getEcoUserId())
                .orElseThrow(() -> new NotFoundException("User with id " + reviewDto.getEcoUserId() + " not found"));
        Recipe recipe = recipeRepository.findById(reviewDto.getRecipeId())
                .orElseThrow(() -> new NotFoundException("Recipe with id " + reviewDto.getRecipeId() + " not found"));

        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setRate(reviewDto.getRate());
        review.setContent(reviewDto.getContent());
        review.setDateCreated(reviewDto.getDateCreated());
        review.setEcoUser(ecoUser);
        review.setRecipe(recipe);
        return review;
    }
}
