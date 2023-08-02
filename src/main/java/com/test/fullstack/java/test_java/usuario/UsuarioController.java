package com.test.fullstack.java.test_java.usuario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
@RestController
@RequestMapping("/api")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository repository;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, UsuarioRepository repository) {
        this.usuarioService = usuarioService;
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/usuarios")
    public List<Usuario> getUsuarios(@RequestParam(required = false) String nombre, @RequestParam(required = false) String estado) {
        if (estado != null) {
            return repository.findByEstado(estado);
        } else if (nombre != null) {
            return repository.findByNombreCompleto("%" + nombre + "%");
        } 
        return usuarioService.getUsuarios();
    }

    @PostMapping("/usuarios")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public void agregarUsuario(@RequestBody Usuario usuario) {
        
        usuarioService.agregarUsuario(usuario);
    }

    @PatchMapping("/usuarios/{id}")
    public void actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuarioService.patchUsuario(id, usuario);
    }
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Long> eliminarUsuario(@PathVariable Long id) {

        var eliminar = usuarioService.eliminarUsuario(id);
        if (!eliminar) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }   

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
    @GetMapping("/usuarios/{id}")
    public List<Usuario> getUsuario(@PathVariable Long id) {
        return usuarioService.getUsuario(id);
    }   
      
}
