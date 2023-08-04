package com.test.fullstack.java.test_java.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

   

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(SessionStatus status) {
        status.setComplete();
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok().body("Logged out successfully");
    }
}
