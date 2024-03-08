package com.epam.ecobites.data;

import com.epam.ecobites.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // custom queries..
}
