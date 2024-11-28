package com.example.biblioteca.service;

import com.example.biblioteca.entity.Usuario;
import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> obtenerPorUsername(String username);

    Usuario guardarUsuario(Usuario usuario);
}
