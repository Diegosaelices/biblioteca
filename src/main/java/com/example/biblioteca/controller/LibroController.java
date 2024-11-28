package com.example.biblioteca.controller;

import com.example.biblioteca.entity.Libro;
import com.example.biblioteca.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public String listarLibros(Model model) {
        model.addAttribute("libros", libroService.obtenerTodos());
        return "libros/lista";
    }

    @GetMapping("/crear")
    public String crearLibroForm(Model model) {
        model.addAttribute("libro", new Libro());
        return "libros/crear";
    }

    @PostMapping
    public String guardarLibro(@ModelAttribute @Valid Libro libro, BindingResult result, @RequestParam("portadaFile") MultipartFile portadaFile) {
        if (result.hasErrors()) {
            return "libros/crear";
        }
        if (!portadaFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + portadaFile.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);
            try {
                Files.write(path, portadaFile.getBytes());
                libro.setPortada("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        libroService.guardar(libro);
        return "redirect:/libros";
    }

    @GetMapping("/editar/{id}")
    public String editarLibroForm(@PathVariable Long id, Model model) {
        Libro libro = libroService.obtenerPorId(id).orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));
        model.addAttribute("libro", libro);
        return "libros/editar";
    }

    @PostMapping("/{id}")
    public String actualizarLibro(@PathVariable Long id, @ModelAttribute @Valid Libro libro, BindingResult result) {
        if (result.hasErrors()) {
            return "libros/editar";
        }
        libroService.guardar(libro);
        return "redirect:/libros";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable Long id) {
        libroService.eliminar(id);
        return "redirect:/libros";
    }
}
