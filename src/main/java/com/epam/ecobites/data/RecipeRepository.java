package com.epam.ecobites.data;

import com.epam.ecobites.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByNameLike(String name);
    Optional<Recipe> findByName(String name);

}
