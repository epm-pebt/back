package com.epam.ecobites.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

// User Entity
@Entity
@Table(name = "ECO_USER")
@Data
public class EcoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String image;
    private Date dateCreated;

    @OneToMany(mappedBy = "ecoUser")
    private List<Review> reviews;

    @OneToMany(mappedBy = "ecoUser")
    private List<ShoppingItem> shoppingItems;
}