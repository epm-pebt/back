package com.epam.ecobites.data;

import com.epam.ecobites.domain.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
    List<RecipeStep> findByRecipeID(Long recipeID);
}
