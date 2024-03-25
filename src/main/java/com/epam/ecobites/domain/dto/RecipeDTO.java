package com.epam.ecobites.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @Max(value = 80, message = "Name can have a maximum of 80 characters")
    private String name;

    @Min(value = 1, message = "Time must be at least 1")
    private int time;

    @NotBlank(message = "Image is required")
    private String image;
}