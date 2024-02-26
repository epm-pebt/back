package com.epam.ecobites.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @Operation(summary = "Health check endpoint",
        description = "Endpoint to check if the application is up and running")
    @ApiResponse(responseCode = "200", description = "Application is up and running")
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {

        return ResponseEntity.ok("Application is up and running");
    }
}