package com.project.security.services;

import com.project.security.user.Role;
import com.project.security.user.User;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.security.config.JwtService;
import com.project.security.controllers.AuthenticationRequest;
import com.project.security.controllers.AuthenticationResponse;
import com.project.security.controllers.RegisterRequest;
import com.project.security.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEconder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // CREATE ACCOUNT
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User
        .builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEconder.encode(request.getPassword()))
        .role(Role.USER)
        .build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
        .builder()
        .Token(jwtToken)
        .build();
    }

    // Login
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        ); 

        // if pass the authenticate meas the email and password are correct
        var user = repository
            .findByEmail(request.getEmail())
            .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        
        return AuthenticationResponse
        .builder()
        .Token(jwtToken)
        .build();
    }

}
