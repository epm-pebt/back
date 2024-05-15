package com.epam.ecobites.data;

import com.epam.ecobites.domain.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    // custom queries..
}
