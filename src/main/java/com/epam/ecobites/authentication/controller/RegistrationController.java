package com.epam.ecobites.authentication.controller;

import com.epam.ecobites.authentication.model.RegistrationRequest;
import com.epam.ecobites.authentication.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestHeader String id,
            @RequestHeader String username,
            @RequestHeader String email,
            @RequestHeader String password,
            @RequestHeader String imageUrl
    ) {
        if (id != null && username != null && email != null && password != null && imageUrl != null) {
            try {
                registrationService.register(new RegistrationRequest(Integer.parseInt(id), username, email, password, imageUrl));
                return  ResponseEntity.ok().build();
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("NumberFormatException");
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
