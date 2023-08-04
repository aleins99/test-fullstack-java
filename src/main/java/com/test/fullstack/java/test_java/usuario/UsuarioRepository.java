package com.test.fullstack.java.test_java.usuario;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nombreCompleto) LIKE LOWER(:nombreCompleto)")
    List<Usuario> findByNombreCompleto(@Param("nombreCompleto") String nombreCompleto);    
    List<Usuario> findByEstado(String estado);
    Optional<Usuario> findOneByEmailAndContrasenia(String email, String contrasenia);
}
