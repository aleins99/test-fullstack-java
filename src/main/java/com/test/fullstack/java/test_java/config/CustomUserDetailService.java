package com.test.fullstack.java.test_java.config;

import com.test.fullstack.java.test_java.usuario.Usuario;
import com.test.fullstack.java.test_java.usuario.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service("userDetailsService")
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Usuario customer = userRepository.findByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException("ead" + email);
        }
        boolean enabled = customer.getEstado().equals("ACTIVO") ? true : false; 
        UserDetails user = User.withUsername(customer.getEmail())
                .password(customer.getContrasenia())
                .disabled(customer.getEstado().equals("INACTIVO") ? true : false)
                .authorities(getAuthorities(customer)).build()
                ;

        return user;
    }

    private Collection<GrantedAuthority> getAuthorities(Usuario user){
        Collection<GrantedAuthority> authorities = new ArrayList<>(1);
        authorities.add(new SimpleGrantedAuthority(user.getRol().toUpperCase()));
        return authorities;
    }
}