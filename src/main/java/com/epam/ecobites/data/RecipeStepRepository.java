package com.epam.ecobites.data;

import com.epam.ecobites.domain.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
    List<RecipeStep> findByRecipeId(Long recipeId);
}
