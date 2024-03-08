package com.epam.ecobites.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "RECIPE_STEP")
public class RecipeStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Short number;
    private String title;
    private String description;
    private String image;

    @ManyToOne
    @JoinColumn(name = "RecipeID")
    private Recipe recipe;
}
