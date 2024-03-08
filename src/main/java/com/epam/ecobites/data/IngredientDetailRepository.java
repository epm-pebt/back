package com.epam.ecobites.data;

import com.epam.ecobites.domain.IngredientDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientDetailRepository extends JpaRepository<IngredientDetail, Long> {
    // custom queries..
}
