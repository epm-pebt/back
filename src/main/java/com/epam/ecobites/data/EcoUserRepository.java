package com.epam.ecobites.data;

import com.epam.ecobites.domain.EcoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EcoUserRepository extends JpaRepository<EcoUser, Long> {
    Optional<EcoUser> findByUsername(String username);
}