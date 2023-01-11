package com.project.security.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.security.controllers.AuthenticationResponse;
import com.project.security.controllers.AuthenticationRequest;
import com.project.security.controllers.RegisterRequest;
import com.project.security.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class authenticationContoller {

    private final AuthenticationService service;

    /*
     * authenticationResponse -> token
     * authenticationRequest -> email, pasword
     * RegisterRequest -> firstname;lastname;email;password;
     */
    @PostMapping("/register")// create account
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate") // like login
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

}
