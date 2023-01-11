package com.project.security.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/*
 * A filter is an object used to intercept the HTTP requests and responses of your application. 
 * By using filter, we can perform two operations at two instances − 
 * Before sending the request to the controller. Before sending a response to the client.
 */
// OncePerRequestFilter - Filter base class that guarantees to be just executed once per request, 
// on any servlet container.

@Component
@RequiredArgsConstructor // auto-implement a constructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService JwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Authorization in header has the token
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // if token doesn't exist break the chain
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        // to extract information from token we need to use a specific class
        userEmail = JwtService.extractUsername(jwt);//extract the userEmail from jwt token
        
    }

}
