package com.MovieGuess.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;
    @Value("${jwt.expiration}")
    private Long EXPIRATION_TIME;

    public String generateToken(UserDetails userDetails) {
        Key signingKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));  // Generate Key from secret
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role","ROLE_" + userDetails.getAuthorities().stream().findFirst().get().getAuthority())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 1 hour expiry
                .signWith(signingKey, SignatureAlgorithm.HS256) // Use the generated signing key
                .compact();
    }

    // Extract username from token
    public String getUsername(String token) {
        //        System.out.println("Extracted username from JWT: " + username);
        return extractClaim(token, Claims::getSubject);
    }

    // Get expiration date from token
    public Date getExpirationDateFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract any claim from the JWT token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder() // Updated to parserBuilder in newer versions
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8))) // Same Key generation method
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    // Check if the token is valid
    public boolean validToken(String token, UserDetails userDetails) {
        return getUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }
}

