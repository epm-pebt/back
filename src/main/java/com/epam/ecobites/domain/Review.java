package com.epam.ecobites.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "REVIEW")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rate;
    private String content;
    private Date dateCreated;

    @ManyToOne
    @JoinColumn(name = "userid")
    private EcoUser ecoUser;

    @ManyToOne
    @JoinColumn(name = "recipeid")
    private Recipe recipe;

}
