package com.epam.ecobites.data;

import com.epam.ecobites.domain.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
    // custom queries..
}
