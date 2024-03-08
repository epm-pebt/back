package com.epam.ecobites.data;

import com.epam.ecobites.domain.ShoppingItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingItemDetailRepository extends JpaRepository<ShoppingItemDetail, Long> {
    // custom queries..
}
