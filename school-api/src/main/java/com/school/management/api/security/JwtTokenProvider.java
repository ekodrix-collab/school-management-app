package com.school.management.api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return buildToken(user);
    }

    public String generateTokenFromUser(CustomUserDetails user) {
        return buildToken(user);
    }

    private String buildToken(CustomUserDetails user) {
        Date now    = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                // subject = userId (unique identifier — no mobile in token)
                .setSubject(user.getUserId().toString())
                // ── Claims ──────────────────────────────────────────────────
                .claim("userId", user.getUserId().toString())   // unique user_id
                .claim("role",   user.getRole())     // e.g. ROLE_ADMIN
                // ── Time claims (iat + exp) ──────────────────────────────────
                .setIssuedAt(now)                    // iat — issued at
                .setExpiration(expiry)               // exp — expiry date & time
                // ── Signature ───────────────────────────────────────────────
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ── Claim extractors ──────────────────────────────────────────────────────

    public UUID getUserIdFromToken(String token) {
        return UUID.fromString(parseClaims(token).get("userId", String.class));
    }

    public String getRoleFromToken(String token) {
        return parseClaims(token).get("role", String.class);
    }

    public Date getIssuedAtFromToken(String token) {
        return parseClaims(token).getIssuedAt();
    }

    public Date getExpiryFromToken(String token) {
        return parseClaims(token).getExpiration();
    }

    // ── Validation ────────────────────────────────────────────────────────────

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("JWT token has expired", ex);
        } catch (UnsupportedJwtException ex) {
            throw new RuntimeException("JWT token is unsupported", ex);
        } catch (MalformedJwtException ex) {
            throw new RuntimeException("JWT token is malformed", ex);
        } catch (SecurityException ex) {
            throw new RuntimeException("JWT signature is invalid", ex);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("JWT claims are empty", ex);
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}