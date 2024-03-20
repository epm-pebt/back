package com.epam.ecobites.authentication.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestLoginController {

    @GetMapping("/loginTest")
    public String loginTest() {
        return "200 OK";
    }
}
