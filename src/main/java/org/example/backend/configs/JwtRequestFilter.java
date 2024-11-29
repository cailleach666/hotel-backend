package org.example.backend.configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
@Order(Integer.MIN_VALUE)
public class JwtRequestFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        Optional<String> jwt = getToken(request);

        if (jwt.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            Claims claims = parseToken(jwt.get());

            // Set the authentication context
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(buildAuthenticationToken(claims));
            log.info("Authentication set for user: {}", claims.getSubject());
        } catch (Exception e) {
            // Log token parsing or authentication failures (optional)
            log.error("Error parsing JWT token: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT token");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String headerPrefix = "Bearer ";
        log.info("Authorization header: {}", header);
        if (header == null || !header.startsWith(headerPrefix)) {
            log.warn("No Bearer token found in request.");
            return Optional.empty();
        }
        log.info("Token found in request: {}", header.substring(headerPrefix.length()));
        return Optional.of(header.substring(headerPrefix.length()));
    }

    private Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JwtTokenProvider.key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private UsernamePasswordAuthenticationToken buildAuthenticationToken(Claims claims) {
        List<String> roles = (List<String>) claims.get("roles");

        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
    }

}
