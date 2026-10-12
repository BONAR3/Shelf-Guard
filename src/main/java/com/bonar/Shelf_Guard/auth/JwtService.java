package com.bonar.Shelf_Guard.auth;

import com.bonar.Shelf_Guard.entities.Users;
import com.bonar.Shelf_Guard.entities.Staff;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtService {

    @Value("${JWT_SECRET}")
    private String secretKey;
    @Value("${JWT_EXPIRATION}")
    private long tokenExpiration;

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(Users users, String sessionId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("accountId", users.getId());
        claims.put("accountType", users.getAccountType().name());
        claims.put("sessionId", sessionId);

        if (users instanceof Staff staff) {
            claims.put("role", staff.getRole().getName());
        }

        return Jwts.builder()
                .claims(claims)
                .subject(users.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    public TokenClaims extractTokenClaims(String token) {
        Claims claims = extractAllClaims(token);

        return TokenClaims.builder()
                .email(claims.getSubject())
                .accountId((String) claims.get("accountId"))
                .accountType(claims.get("accountType", String.class))
                .role(claims.get("role", String.class))
                .sessionId(claims.get("sessionId", String.class))
                .build();
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

}
