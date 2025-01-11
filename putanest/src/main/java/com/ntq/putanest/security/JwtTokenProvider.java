package com.ntq.putanest.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final String JWT_SECRET = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";  // Mã hóa bí mật
    private final long JWT_EXPIRATION = 86400000L; // Thời gian sống của token (1 ngày)

    // Tạo token từ Authentication
    public String generateToken(Authentication authentication, Integer userId) {
        String username = authentication.getName();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    // Lấy username từ token
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Long getUserIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class); // Đọc userId từ payload
    }

    // Lấy authorities (roles) từ token
    public List<SimpleGrantedAuthority> getAuthorities(String token) {
        return ((List<?>) Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .get("roles"))
                .stream()
                .map(role -> new SimpleGrantedAuthority((String) role))
                .collect(Collectors.toList());
    }

    // Kiểm tra token có hợp lệ không
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
            return false;
        }
    }

    // Lấy token từ header
    public String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }
}