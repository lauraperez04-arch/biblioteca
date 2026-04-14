package com.unicatolica.biblioteca.service;

import com.unicatolica.biblioteca.dto.UsuarioCreateRequest;
import com.unicatolica.biblioteca.dto.UsuarioResponse;
import com.unicatolica.biblioteca.dto.UsuarioUpdateRequest;
import com.unicatolica.biblioteca.entity.Usuario;
import com.unicatolica.biblioteca.repository.UsuarioRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioResponse crear(UsuarioCreateRequest req) {
        if (usuarioRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("El correo ya existe");
        }
        Usuario u = new Usuario();
        u.setNombre(req.getNombre());
        u.setEmail(req.getEmail());
        u.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        u.setActivo(true);
        usuarioRepository.save(u);
        return toResponse(u);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obtener(Long id) {
        Usuario u = usuarioRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return toResponse(u);
    }

    @Transactional
    public UsuarioResponse actualizar(Long id, UsuarioUpdateRequest req) {
        Usuario u = usuarioRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        if (!u.getEmail().equals(req.getEmail()) && usuarioRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("El correo ya existe");
        }
        u.setNombre(req.getNombre());
        u.setEmail(req.getEmail());
        u.setActivo(req.getActivo());
        usuarioRepository.save(u);
        return toResponse(u);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponse toResponse(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNombre(), u.getEmail(), u.getActivo());
    }
}
