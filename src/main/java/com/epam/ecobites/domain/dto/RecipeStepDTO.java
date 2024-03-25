package com.epam.ecobites.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeStepDTO {
    private Long id;

    @NotNull(message = "Number is required")
    private Short number;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private String image;

    @NotNull(message = "Recipe ID is required")
    private Long recipeId;
}
