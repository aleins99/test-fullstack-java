
package com.test.fullstack.java.test_java.usuario;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository repository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    public void testGetUsuarios() throws Exception {
        Usuario user1 = new Usuario();
        user1.setId(1L);
        user1.setEmail("alex@gmail.com");
        user1.setRol("CONSULTOR");
        user1.setEstado("ACTIVO");
        user1.setContrasenia("dasdad");
        Usuario user2 = new Usuario();
        user1.setId(2L);
        user1.setEmail("alex2@gmail.com");
        user1.setRol("CONSULTOR");
        user1.setEstado("ACTIVO");
        user1.setContrasenia("dasdad");

        List<Usuario> users = Arrays.asList(user1, user2);

        when(usuarioService.getUsuarios()).thenReturn(users);

        mockMvc.perform(get("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(usuarioService, times(1)).getUsuarios();
    }

    @Test
    public void testAgregarUsuario() throws Exception {
        Usuario user = new Usuario();
        // set user properties
        user.setId(1L);
        user.setEmail("alex@gmail.com");
        user.setRol("CONSULTOR");
        user.setEstado("ACTIVO");
        user.setContrasenia("dasdad");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated());

        verify(usuarioService, times(1)).agregarUsuario(any(Usuario.class));
    }
    @Test
    public void eliminarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombreCompleto("John Doe");
        usuario.setContrasenia("password");
        usuario.setEmail("john@doe.com");
        usuario.setEstado("ACTIVO");

        List<Usuario> usuarios = Arrays.asList(usuario);
        when(usuarioService.eliminarUsuario(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
