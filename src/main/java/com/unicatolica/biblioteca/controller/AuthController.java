package com.unicatolica.biblioteca.controller;

import com.unicatolica.biblioteca.dto.LoginRequest;
import com.unicatolica.biblioteca.dto.TokenResponse;
import com.unicatolica.biblioteca.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest body) {
        String token = authService.login(body.getEmail(), body.getPassword());
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
