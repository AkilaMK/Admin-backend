package com.example.admin.security;

import com.example.admin.data.model.Role;
import com.example.admin.data.model.User;
import com.example.admin.dto.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.web}")
    private long jwtWebExpirationInMils;

    public String generateToken(Authentication authentication) {
        // Generate JWT token
        Key key = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS512.getJcaName());

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        List<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        // Add custom claims to JWT token
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", roleNames);

        Date expiryDate = new Date(System.currentTimeMillis() + jwtWebExpirationInMils);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .addClaims(claims)
                .signWith(key)
                .compact();
    }



    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (IncorrectClaimException ex) {
            log.error("Invalid claim", ex);
        } catch (MalformedJwtException ex) {
            log.error("Invalid token", ex);
        } catch (ExpiredJwtException ex) {
            log.error("Expired token", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported token", ex);
        } catch (IllegalArgumentException ex) {
            log.error("Empty or null token", ex);
        } catch (Exception ex) {
            log.error("Failed to validate the token", ex);
        }
        return false;
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public List<GrantedAuthority> extractAuthorities(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        List<String> authorities = (List<String>) claims.get("authorities");
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

