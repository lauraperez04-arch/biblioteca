package com.unicatolica.biblioteca.service;

import com.unicatolica.biblioteca.entity.Usuario;
import com.unicatolica.biblioteca.repository.UsuarioRepository;
import com.unicatolica.biblioteca.security.JwtService;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(String email, String password) {
        Optional<Usuario> opt = usuarioRepository.findByEmail(email);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Credenciales invalidas");
        }
        Usuario u = opt.get();
        boolean ok;
        try {
            ok = passwordEncoder.matches(password, u.getPasswordHash());
        } catch (IllegalArgumentException ex) {
            ok = false;
        }
        if (!ok) {
            throw new IllegalArgumentException("Credenciales invalidas");
        }
        if (!Boolean.TRUE.equals(u.getActivo())) {
            throw new IllegalArgumentException("Usuario inactivo");
        }
        return jwtService.generateToken(String.valueOf(u.getId()));
    }
}
