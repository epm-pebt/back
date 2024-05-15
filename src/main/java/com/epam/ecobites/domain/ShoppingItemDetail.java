package com.epam.ecobites.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "SHOPPING_ITEM_DETAIL")
public class ShoppingItemDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private String unit;

    @ManyToOne
    @JoinColumn(name = "ShoppingItemID")
    private ShoppingItem shoppingItem;

}
