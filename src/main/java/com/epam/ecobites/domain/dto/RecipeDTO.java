package com.epam.ecobites.domain.dto;

import com.epam.ecobites.domain.DietCategory;
import com.epam.ecobites.domain.DishType;
import jakarta.validation.constraints.Min;
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
public class RecipeDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Dish type is required")
    private DishType dishType;

    @NotNull(message = "Diet category is required")
    private DietCategory dietCategory;

    @Min(value = 1, message = "Time must be at least 1")
    private int time;

    @NotBlank(message = "Summary is required")
    private String summary;

    private String image;
}