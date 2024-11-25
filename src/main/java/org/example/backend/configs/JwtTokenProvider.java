package org.example.backend.configs;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtTokenProvider {

    private final long TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 10;
    private static byte[] keyBytes = Decoders.BASE64
            .decode("YCLg7UP+zuwRxl9Y6kP8C2Kbp8s6BK4MW87iFc39XYM=");

    public static final Key key = Keys.hmacShaKeyFor(keyBytes);

    public String getToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject("sub")
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(key)
                .compact();
    }
}
