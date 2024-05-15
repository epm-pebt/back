package com.epam.ecobites.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "SHOPPING_ITEM_DETAIL")
public class ShoppingItemDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private String unit;

    @OneToMany(mappedBy = "shoppingItemDetail")
    private List<ShoppingItem> shoppingItem;
}
