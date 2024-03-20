package com.epam.ecobites.authentication.service;

import com.epam.ecobites.authentication.model.RegistrationRequest;
import com.epam.ecobites.data.EcoUserRepository;
import com.epam.ecobites.domain.EcoUser;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegistrationService {
    private final EcoUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(EcoUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(@NotNull RegistrationRequest registrationRequest) {
        EcoUser user = new EcoUser();
        user.setId((long) registrationRequest.id());
        user.setUsername(registrationRequest.username());
        user.setEmail(registrationRequest.email());
        user.setPassword(passwordEncoder.encode(registrationRequest.password()));
        user.setImage(registrationRequest.imageUrl());
        user.setDateCreated(new Date(System.currentTimeMillis()));
        repository.save(user);
    }
}
