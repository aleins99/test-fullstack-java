package com.test.fullstack.java.test_java.usuario;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import com.test.fullstack.java.test_java.config.CustomUserDetailService;
import com.test.fullstack.java.test_java.config.JWTGenerator;
import com.test.fullstack.java.test_java.login.AuthResponse;
import com.test.fullstack.java.test_java.login.Login;
import com.test.fullstack.java.test_java.login.LoginMesage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository repository;
    private AuthenticationManager authenticationManager;
    private final CustomUserDetailService userDetailsService;
    private final JWTGenerator jwtGenerator;
    @Autowired
    public UsuarioController(UsuarioService usuarioService, UsuarioRepository repository, AuthenticationManager authenticationManager, CustomUserDetailService userDetailsService, JWTGenerator jwtUt) {
        this.usuarioService = usuarioService;
        this.repository = repository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtGenerator = jwtUt;
    }
    // usuarios?nombre=nombre&estado=estado  || usuarios?nombre=nombre || usuarios?estado=estado
    @RequestMapping(method = RequestMethod.GET, path = "/usuarios")
    public List<Usuario> getUsuarios(@RequestParam(required = false) String nombre, @RequestParam(required = false) String estado) {
        if (estado != null) {
            return repository.findByEstado(estado);
        } else if (nombre != null) {
            return repository.findByNombreCompleto("%" + nombre + "%");
        } 
        System.out.println("getUsuarios asdasdadsadsasd");
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

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody Login loginRequest) throws Exception {
        Usuario us = repository.findByEmail(loginRequest.getEmail());
        if (us == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        String estado = us.getEstado();
        if (estado.equals("INACTIVO")) {
            return new ResponseEntity<>("Usuario Inactivo", HttpStatus.FORBIDDEN);
        }
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
            loginRequest.getEmail(),
            loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);

    }


    
}
