import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.fullstack.java.test_java.usuario.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private UserDetailsManager userDetailsManager;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void agregarUsuario_deberiaRetornar201() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNombre("John");
        usuario.setApellido("Doe");
        usuario.setEmail("johndoe@example.com");
        usuario.setContrasenia("password");
        usuario.setRol("USER");

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "consultor", roles = "CONSULTOR")
    public void obtenerUsuario_deberiaRetornar200() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombreCompleto("John Doe");
        usuario.setEmail("johndoe@example.com");
        usuario.setContrasenia("password");
        usuario.setRol("USER");

        given(usuarioService.obtenerUsuario(1L)).willReturn(usuario);

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombreCompleto", is("John Doe")))
                .andExpect(jsonPath("$.email", is("johndoe@example.com")))
                .andExpect(jsonPath("$.contrasenia").doesNotExist())
                .andExpect(jsonPath("$.rol", is("USER")));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void agregarUsuario_deberiaRetornar403() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNombre("John");
        usuario.setApellido("Doe");
        usuario.setEmail("johndoe@example.com");
        usuario.setContrasenia("password");
        usuario.setRol("USER");

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isForbidden());
    }


}