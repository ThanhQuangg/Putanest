package com.ntq.putanest.security;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Lấy token từ header
            String token = jwtTokenProvider.getTokenFromRequest(request);

            // Kiểm tra token
            if (token != null && jwtTokenProvider.validateToken(token)) {
                // Lấy thông tin người dùng từ token
                String username = jwtTokenProvider.getUsernameFromToken(token);
                Long userId = jwtTokenProvider.getUserIdFromToken(token);

                if (username != null) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            username, null, jwtTokenProvider.getAuthorities(token)
                    );
                    // Đặt đối tượng Authentication vào SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    request.setAttribute("userId", userId);
                }
            }
        } catch (ExpiredJwtException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token đã hết hạn.");
            return;
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token không hợp lệ.");
            return;
        }

        // Tiếp tục filter chain
        filterChain.doFilter(request, response);
    }

}