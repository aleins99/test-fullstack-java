package com.test.fullstack.java.test_java.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getUsuarios() {
        Iterable<Usuario> usuarios = usuarioRepository.findAll();
        List<Usuario> usuariosList = new ArrayList<Usuario>();
        usuarios.forEach(usuario -> {
            usuariosList.add(usuario);
        });
        return usuariosList;
    }

    public Usuario agregarUsuario(Usuario usuario) {
        if (null == usuario) {
            throw new RuntimeException("El usuario no puede ser nulo");
        } else {
            Usuario usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
            if(usuarioExistente != null) {
                throw new IllegalArgumentException("El correo electrónico ya está en uso");
            }
            return usuarioRepository.save(usuario);
        }
    }
    public boolean eliminarUsuario(Long id) {
        // si el usuario no existe retorna false
        return this.usuarioRepository.findById(id).map(usuario -> {
            this.usuarioRepository.delete(usuario);
            return true;
        }).orElse(false);
    } 
    public void patchUsuario(Long id, Usuario usuario) {
        if (null == usuario) {
            throw new RuntimeException("El usuario no puede ser nulo");
        } else {
            Optional<Usuario> optionalUsuario = this.usuarioRepository.findById(id);
            if (optionalUsuario.isPresent()) {
                Usuario usuarioExistente = optionalUsuario.get();
                if (usuario.getNombreCompleto() != null) {
                    usuarioExistente.setNombreCompleto(usuario.getNombreCompleto());
                }
             
                if (usuario.getEmail() != null) {
                    usuarioExistente.setEmail(usuario.getEmail());
                }
                if (usuario.getEstado().equals("ACTIVO") || usuario.getEstado().equals("INACTIVO"))  {
                    usuarioExistente.setEstado(usuario.getEstado());
                }
                if (usuario.getRol() != null) {
                    usuarioExistente.setRol(usuario.getRol());
                }
                // Actualizar otros campos según sea necesario
                this.usuarioRepository.save(usuarioExistente);
            } else {
                throw new RuntimeException("El usuario no existe");
            }
        }
    }
    public List<Usuario> getUsuario(Long id) {
        Optional<Usuario> usuarios = usuarioRepository.findById(id);
        List<Usuario> usuariosList = new ArrayList<Usuario>();
        usuariosList.add(usuarios.get());
        
        return usuariosList;
    }

 
   
}
