package com.test.fullstack.java.test_java.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select USUARIO_ID, CONTRASENIA, ESTADO from USUARIOS where USUARIO_ID=?");

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select USUARIO_ID, ROL from USUARIOS where USUARIO_ID=?");

        return jdbcUserDetailsManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, "api/usuarios").hasAnyRole("ADMIN", "CONSULTOR")
            .antMatchers(HttpMethod.POST, "api/usuarios").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "api/usuarios").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "api/usuarios").hasRole("ADMIN")
            .antMatchers(HttpMethod.GET, "api/usuarios/**").hasAnyRole("ADMIN", "CONSULTOR")
            .anyRequest().authenticated()   
            .and()
            .formLogin()
            .loginPage( "/login")
            .permitAll()
            .and()
            .logout()
            .permitAll()
            .and()
            .csrf().disable();

    }
   
}
