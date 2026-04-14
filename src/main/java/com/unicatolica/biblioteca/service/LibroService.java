package com.unicatolica.biblioteca.service;

import com.unicatolica.biblioteca.dto.LibroCreateRequest;
import com.unicatolica.biblioteca.dto.LibroResponse;
import com.unicatolica.biblioteca.dto.LibroUpdateRequest;
import com.unicatolica.biblioteca.entity.Libro;
import com.unicatolica.biblioteca.repository.LibroRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Transactional
    public LibroResponse crear(LibroCreateRequest req) {
        Libro l = new Libro();
        l.setTitulo(req.getTitulo());
        l.setAutor(req.getAutor());
        l.setAnioPublicacion(req.getAnioPublicacion());
        l.setIsbn(req.getIsbn());
        libroRepository.save(l);
        return toResponse(l);
    }

    @Transactional(readOnly = true)
    public List<LibroResponse> listar() {
        return libroRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public LibroResponse obtener(Long id) {
        Libro l = libroRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));
        return toResponse(l);
    }

    @Transactional
    public LibroResponse actualizar(Long id, LibroUpdateRequest req) {
        Libro l = libroRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));
        l.setTitulo(req.getTitulo());
        l.setAutor(req.getAutor());
        l.setAnioPublicacion(req.getAnioPublicacion());
        l.setIsbn(req.getIsbn());
        libroRepository.save(l);
        return toResponse(l);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new IllegalArgumentException("Libro no encontrado");
        }
        libroRepository.deleteById(id);
    }

    private LibroResponse toResponse(Libro l) {
        return new LibroResponse(l.getId(), l.getTitulo(), l.getAutor(), l.getAnioPublicacion(), l.getIsbn());
    }
}
