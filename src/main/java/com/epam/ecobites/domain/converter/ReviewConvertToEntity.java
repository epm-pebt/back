package com.epam.ecobites.domain.converter;

import com.epam.ecobites.data.EcoUserRepository;
import com.epam.ecobites.data.RecipeRepository;
import com.epam.ecobites.domain.EcoUser;
import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.Review;
import com.epam.ecobites.domain.dto.ReviewDTO;
import com.epam.ecobites.exception.NotFoundException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReviewConvertToEntity implements Converter<ReviewDTO, Review> {
    private EcoUserRepository ecoUserRepository;
    private RecipeRepository recipeRepository;

    public ReviewConvertToEntity(EcoUserRepository ecoUserRepository, RecipeRepository recipeRepository) {
        this.ecoUserRepository = ecoUserRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Review convert(ReviewDTO source) {
        EcoUser ecoUser = ecoUserRepository.findById(source.getEcoUserId())
                .orElseThrow(() -> new NotFoundException("User with id " + source.getEcoUserId() + " not found"));
        Recipe recipe = recipeRepository.findById(source.getRecipeId())
                .orElseThrow(() -> new NotFoundException("Recipe with id " + source.getRecipeId() + " not found"));

        Review review = new Review();
        review.setId(source.getId());
        review.setRate(source.getRate());
        review.setContent(source.getContent());
        review.setDateCreated(source.getDateCreated());
        review.setEcoUser(ecoUser);
        review.setRecipe(recipe);
        return review;
    }
}
