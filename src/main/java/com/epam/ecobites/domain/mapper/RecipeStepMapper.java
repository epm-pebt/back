package com.epam.ecobites.domain.mapper;

import com.epam.ecobites.domain.Recipe;
import com.epam.ecobites.domain.RecipeStep;
import com.epam.ecobites.domain.dto.RecipeDto;
import com.epam.ecobites.domain.dto.RecipeStepDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeStepMapper {
    RecipeStepDto toRecipeStepDto(RecipeStep step);
}
