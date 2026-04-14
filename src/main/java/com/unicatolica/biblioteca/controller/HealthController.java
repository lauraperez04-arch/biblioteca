package com.unicatolica.biblioteca.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/")
    public Map<String, String> salud() {
        return Map.of(
                "message", "API Biblioteca activa",
                "build", "sin-spring-security-web-v2");
    }
}
