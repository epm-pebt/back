package com.epam.ecobites.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
    private long id;
    private String name;
    private int time;
    private String image;
    private String dishType;
    private String dietCategory;
}
