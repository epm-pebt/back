package com.epam.ecobites.authentication.service;

import com.epam.ecobites.authentication.model.RegistrationRequest;
import com.epam.ecobites.authentication.test.TestEcoUserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final TestEcoUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(TestEcoUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(@NotNull RegistrationRequest registrationRequest) {
        repository.addUser(
                registrationRequest.id(),
                registrationRequest.username(),
                registrationRequest.email(),
                passwordEncoder.encode(registrationRequest.password()),
                registrationRequest.imageUrl()
        );
    }
}
