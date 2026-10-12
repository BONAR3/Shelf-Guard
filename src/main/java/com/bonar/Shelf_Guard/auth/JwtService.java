package com.bonar.Shelf_Guard.auth;

import com.bonar.Shelf_Guard.entities.Account;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtService {

    @Value("${JWT_SECRET}")
    private String secretKey;

    @Value("${JWT_EXPIRATION}")
    private long expirationMs;

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Account account) {
        return Jwts.builder()
                .subject(account.getEmail())
                .claim("accountId", account.getId())
                .claim("accountType", account.getClass().getSimpleName()) // Owner, Staff, or SuperAdmin
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSignInKey())
                .compact();
    }

//    private Claims extractAllClaims(String token) {
//        return Jwts.parser()
//                .verifyWith(getSignKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }

}
