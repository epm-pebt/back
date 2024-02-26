package com.epam.ecobites.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HealthCheckControllerTest {
    @InjectMocks
    private HealthCheckController healthCheckController;

    @Test
    void healthCheckTest() {
        // GIVEN-WHEN
        var response = healthCheckController.healthCheck();

        // THEN
        Assertions.assertEquals("Application is up and running", response.getBody());
    }
}
