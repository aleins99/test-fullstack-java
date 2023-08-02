package com.test.fullstack.java.test_java.usuario;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    @Before()
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testGetUsuarios() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombreCompleto("John Doe");
        usuario.setContrasenia("password");
        usuario.setEmail("john@doe.com");
        usuario.setEstado("ACTIVO");

        List<Usuario> usuarios = Arrays.asList(usuario);

        when(usuarioService.getUsuario(1L)).thenReturn(usuarios);

        mockMvc.perform(get("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombreCompleto").value("John Doe"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testAgregarUsuario() throws Exception {
        Usuario us1 = new Usuario(4L,"Alejandro Marín", "ale353@gmail.com", "adeeadss", "ACTIVO", "CONSULTOR");
        when(usuarioService.agregarUsuario(any(Usuario.class)))
                .thenReturn(us1);

        Usuario us2 = new Usuario(8L,"Alejandro Marín", "ale35@gmail.com", "ads", "ACTIVO", "CONSULTOR");
        ObjectMapper objectMapper = new ObjectMapper();
        String usuarioJson = objectMapper.writeValueAsString(us2);
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioJson))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
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
