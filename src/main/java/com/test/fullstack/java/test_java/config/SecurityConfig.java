package com.test.fullstack.java.test_java.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.test.fullstack.java.test_java.usuario.Usuario;
import com.test.fullstack.java.test_java.usuario.UsuarioRepository;
import com.test.fullstack.java.test_java.usuario.UsuarioService;

import javax.sql.DataSource;


@Configuration
public class SecurityConfig  {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired UsuarioRepository userRepository;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                .requestMatchers(HttpMethod.GET, "api/usuarios").hasAnyAuthority("ADMIN", "CONSULTOR")
                .requestMatchers(HttpMethod.POST, "api/usuarios").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "api/usuarios").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "api/usuarios").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "api/usuarios/**").hasAuthority("ADMIN")

        )
                .httpBasic(Customizer.withDefaults()
                );
        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(email -> {
            Usuario usuario = userRepository.findByEmail(email);
            if (usuario != null && usuario.getEstado().equals("ACTIVO")) {
                return new User(usuario.getEmail(), usuario.getContrasenia(),
                        AuthorityUtils.createAuthorityList(usuario.getRol()));
            } else {
                throw new UsernameNotFoundException("Usuario no encontrado o inactivo");
            }
        });
    }
    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }


}
