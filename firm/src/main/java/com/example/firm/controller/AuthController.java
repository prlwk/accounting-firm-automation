package com.example.firm.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.firm.security.jwt.JwtTokenProvider;
import com.example.firm.service.CustomUserDetailsService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = userDetailsService;
    }

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody LinkedMultiValueMap<String, String> map) {
        try {
            String name = map.getFirst("username");
            String password = map.getFirst("password");
            if (!customUserDetailsService.loadUserByUsername(name).getPassword().equals(password)) {
                throw new BadCredentialsException("Invalid password!");
            }
            String token = jwtTokenProvider.createToken(name, customUserDetailsService.loadUserByUsername(name).getAuthorities());
            Map<Object, Object> model = new HashMap<>();
            model.put("username", name);
            model.put("token", token);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException exception) {
            throw new BadCredentialsException("Invalid username or password!");
        }
    }
}
