package com.test.fullstack.java.test_java.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.test.fullstack.java.test_java.usuario.Usuario;
import com.test.fullstack.java.test_java.usuario.UsuarioRepository;
import com.test.fullstack.java.test_java.usuario.UsuarioService;


@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    private UsuarioService usuarioService;

    private UsuarioRepository userRepository;
    private JwtAuthEntryPoint authEntryPoint;
    private CustomUserDetailService userDetailsService;


    @Autowired
    public SecurityConfig(CustomUserDetailService userDetailsService, JwtAuthEntryPoint authEntryPoint, UsuarioRepository userRepository, UsuarioService usuarioService)  {
        this.userDetailsService = userDetailsService;
        this.authEntryPoint = authEntryPoint;
        this.userRepository = userRepository;
        this.usuarioService = usuarioService;

    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(configurer ->
                configurer
                .requestMatchers(HttpMethod.GET, "api/usuarios").hasAnyAuthority("ADMIN", "CONSULTOR")
                .requestMatchers(HttpMethod.POST, "api/usuarios").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "api/usuarios/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "api/usuarios/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "api/usuarios/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        );
        
        http.exceptionHandling(handling -> handling.authenticationEntryPoint(authEntryPoint));
        http.sessionManagement(hd -> hd.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        
        http.formLogin(form ->
            form
                    .loginPage("/api/authenticate")
                    .loginProcessingUrl("/authenticateTheUser")
                    .permitAll());
                    
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);   
        
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



    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") 
        .allowedOrigins("http://localhost:5173") 
        .allowedMethods("*") 
        .allowedHeaders("*")
        .allowCredentials(true);

    }
        
    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    @Bean
    public  JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }
}
