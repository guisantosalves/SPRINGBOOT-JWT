package com.project.security.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/*
 * class to manipulate the token
 * Extract
 * validate
 * etc
 */

@Service
public class JwtService {

    private static final String SECRET_KEY = "3273357638792F423F4528482B4B6250655368566D597133743677397A244326";

    /*
     * Extracting claims from token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // extraindo as reivindicações do token
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // method to get a specific part in jwt
    // Function<funcType(no parametro), return>
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        // getting all the claims
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims); // pass a generinc func to get the data
    }

    // getting a key
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /*
     * Generating TOKEN
     */
    public String generateToken(
            Map<String, Claims> ExtraClaims,
            UserDetails userDetails) {
        return Jwts
        .builder()
        .setClaims(ExtraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    /*
     * Validating token
     */
    // verifica se o email do token é igual ao email do userDetails
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extraxctExpiration(token).before(new Date());
    }

    private Date extraxctExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
