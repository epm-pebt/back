package com.epam.ecobites.data;

import com.epam.ecobites.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    // custom queries..
}