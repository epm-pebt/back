package com.epam.ecobites.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingItemDTO {
    private Long id;

    @NotNull(message = "Recipe ID is required")
    private Long recipeId;

    @NotNull(message = "Ingredient ID is required")
    private Long ingredientId;

    @NotNull(message = "User ID is required")
    private Long ecoUserId;
}