package com.unicatolica.biblioteca.controller;

import com.unicatolica.biblioteca.dto.LibroCreateRequest;
import com.unicatolica.biblioteca.dto.LibroResponse;
import com.unicatolica.biblioteca.dto.LibroUpdateRequest;
import com.unicatolica.biblioteca.service.LibroService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @PostMapping
    public ResponseEntity<LibroResponse> crear(@Valid @RequestBody LibroCreateRequest body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.crear(body));
    }

    @GetMapping
    public List<LibroResponse> listar() {
        return libroService.listar();
    }

    @GetMapping("/{id}")
    public LibroResponse obtener(@PathVariable Long id) {
        return libroService.obtener(id);
    }

    @PutMapping("/{id}")
    public LibroResponse actualizar(@PathVariable Long id, @Valid @RequestBody LibroUpdateRequest body) {
        return libroService.actualizar(id, body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
