package com.test.fullstack.java.test_java.usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.DeferredImportSelector;
@Entity
@Table(name = "USUARIOS")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USUARIO_ID")
    private Long id;

    public Usuario(String nombreCompleto, String email, String contrasenia, String estado, String rol) {
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.contrasenia = contrasenia;
        this.estado = estado;
        this.rol = rol;
    }

    @Column(name = "NOMBRE_COMPLETO")
    private String nombreCompleto;


    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "CONTRASENIA")
    private String contrasenia;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "ROL")
    private String rol;

    public Long getId() {
        return id;
    }

   
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getContrasenia() {
        return contrasenia;
    }
    
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }





    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", email='" + email + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", estado='" + estado + '\'' + ", rol='" + rol + '\'' +
                '}';
    }
}
