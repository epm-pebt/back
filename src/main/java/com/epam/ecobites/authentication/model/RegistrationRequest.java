package com.epam.ecobites.authentication.model;

public record RegistrationRequest(Integer id, String username, String email, String password, String imageUrl) {
}