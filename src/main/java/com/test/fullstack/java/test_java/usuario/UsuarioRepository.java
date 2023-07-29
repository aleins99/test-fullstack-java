package com.test.fullstack.java.test_java.usuario;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}
