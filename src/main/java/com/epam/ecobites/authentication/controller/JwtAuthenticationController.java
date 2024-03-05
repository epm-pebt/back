package com.epam.ecobites.authentication.controller;

import com.epam.ecobites.authentication.service.JwtAuthenticationService;
import com.epam.ecobites.authentication.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtAuthenticationController {
    private final JwtAuthenticationService jwtAuthenticationService;

    @Autowired
    public JwtAuthenticationController(JwtAuthenticationService jwtAuthenticationService) {
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestHeader String username, @RequestHeader String password) {
        if (username != null && password != null) {
            String jwt = jwtAuthenticationService.login(new LoginRequest(username, password));
            if (jwt != null) {
                return ResponseEntity.ok(jwt);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
