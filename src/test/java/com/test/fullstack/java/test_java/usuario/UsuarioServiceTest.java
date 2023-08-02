package com.test.fullstack.java.test_java.usuario;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsuarioServiceTest {
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @BeforeEach
    void setUp() {
        usuarioService = new UsuarioService(usuarioRepository);
    }

    @AfterEach
    void tearDown() {
        usuarioRepository.deleteAll();
    }

    @Test
    void getUsuarios() {
        usuarioRepository.deleteAll();
        Usuario usuario = new Usuario(1L, "Alejandro", "ale@email.com", "alesio12", "ACTIVO", "ADMIN");
        Usuario usuario2 = new Usuario(2L, "Alejandro", "aleE2@email.com", "alesio12E", "ACTIVO", "CONSULTOR");
        Usuario usuario3 = new Usuario(3L, "Alejandro", "aleE2E@email.com", "alesio12E", "ACTIVO", "CONSULTOR");

        usuarioRepository.saveAll(Arrays.asList(usuario, usuario2, usuario3));
        List<Usuario> us = usuarioService.getUsuarios();
        assertEquals(3, us.size());
    }
    @Test
    void getUsuario() {
        Usuario usuario2 = new Usuario(2L, "Alejandro", "aleE2@email.com", "alesio12E", "ACTIVO", "ADMIN");
        usuarioRepository.save(usuario2);
        List<Usuario> us = usuarioService.getUsuario(2L);
        Usuario actual = us.get(0);
        assertEquals(2L, actual.getId());
        assertEquals(2L, actual.getId());
        assertEquals("Alejandro", actual.getNombreCompleto());
        assertEquals("aleE2@email.com", actual.getEmail());
        assertEquals("alesio12E", actual.getContrasenia());
        assertEquals("ACTIVO", actual.getEstado());
        assertEquals("ADMIN", actual.getRol());

    }
    @Test
    void agregarUsuario() {
        Usuario actual = new Usuario(2L, "Alejandro", "aleE2@email.com", "alesio12E", "ACTIVO", "ADMIN");
        usuarioService.agregarUsuario(actual);
        assertEquals(2L, actual.getId());
        assertEquals(2L, actual.getId());
        assertEquals(2L, actual.getId());
        assertEquals("Alejandro", actual.getNombreCompleto());
        assertEquals("aleE2@email.com", actual.getEmail());
        assertEquals("alesio12E", actual.getContrasenia());
        assertEquals("ACTIVO", actual.getEstado());
        assertEquals("ADMIN", actual.getRol());

    }
}