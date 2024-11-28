package com.example.biblioteca.service;

import com.example.biblioteca.entity.Libro;
import java.util.List;
import java.util.Optional;

public interface LibroService {
    List<Libro> obtenerTodos();

    Optional<Libro> obtenerPorId(Long id);

    Libro guardar(Libro libro);

    void eliminar(Long id);
}

