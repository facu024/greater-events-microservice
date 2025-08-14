package ar.edu.unnoba.pdyc2025.event.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private final JwtTokenUtil jwt;

    public JwtAuthorizationFilter(JwtTokenUtil jwt) {
        this.jwt = jwt;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean skip = !path.startsWith("/admin/");
        if (skip) {
            log.debug("Saltando filtro para path: {}", path);
        }
        return skip;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getRequestURI();

        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || authHeader.isBlank()) {
                throw new RuntimeException("Falta header Authorization");
            }

            String token = authHeader.startsWith("Bearer ") ?
                    authHeader.substring(7) : authHeader;

            log.debug("Token recibido (sin Bearer): {}", token);

            jwt.verify(token); // valida firma y expiración

            filterChain.doFilter(request, response);

        } catch (Exception ex) {

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(
                    String.format("{\"error\":\"Unauthorized\",\"message\":\"%s\"}", ex.getMessage())
            );
        }
    }
}