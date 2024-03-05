package com.epam.ecobites.authentication.service;

import com.epam.ecobites.authentication.model.LoginRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public JwtAuthenticationService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String login(@NotNull LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()
                    )
            );
            return jwtService.buildJwt(loginRequest.username());
        } catch (AuthenticationException e) {
            return null;
        }
    }
}