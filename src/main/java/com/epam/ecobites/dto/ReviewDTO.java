package com.epam.ecobites.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {
    private Long id;

    @Min(value = 1, message = "Rate must be at least 1")
    @Max(value = 5, message = "Rate must be at most 5")
    private int rate;

    @NotBlank(message = "Content is required")
    private String content;

    private Date dateCreated;

    @NotNull(message = "User ID is required")
    private Long ecoUserId;

    @NotNull(message = "Recipe ID is required")
    private Long recipeId;
}