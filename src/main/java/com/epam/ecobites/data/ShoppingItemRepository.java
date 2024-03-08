package com.epam.ecobites.data;

import com.epam.ecobites.domain.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {
    // custom queries..
}