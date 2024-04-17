package com.epam.ecobites.domain.mapper;

import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.dto.RecipeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
    RecipeDto toRecipeDto(Recipe recipe);
}
