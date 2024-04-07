package com.example.blog_spring_mvc.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final String secret;

    @Autowired
    public JwtProvider(JwtAuthEntryPoint jwtAuthEntryPoint, @Value("${jwt.secret}") String secret) {
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.secret = secret;

    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication auth) {
        String username = auth.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + 86400000); // 24 heures

        String role = auth.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(()->new IllegalStateException("there is no defined role for this user !"));


        String token = Jwts.builder()
                .setSubject(username)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();

        return token;
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }


}
