package com.epam.ecobites.dto;

import com.epam.ecobites.domain.RecipeIngredient;
import com.epam.ecobites.domain.ShoppingItem;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;
}
