package com.test.fullstack.java.test_java.usuario;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    public ResponseEntity<Object> agregarUsuario(@RequestBody Usuario usuario) {
        usuarioService.agregarUsuario(usuario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @PatchMapping("/usuarios/{id}")
    public void actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuarioService.patchUsuario(id, usuario);
    }
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Long> eliminarUsuario(@PathVariable Long id) {

        var eliminar = usuarioService.eliminarUsuario(id);
        if (!eliminar) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }   

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
    @GetMapping("/usuarios/{id}")
    public List<Usuario> getUsuario(@PathVariable Long id) {
        return usuarioService.getUsuario(id);
    }   
      
}
