package com.epam.ecobites.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeStepDto {
    private Short number;
    private String title;
    private String description;
    private String image;
}
