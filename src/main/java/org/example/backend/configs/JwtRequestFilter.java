package org.example.backend.configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
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
        if (jwt.isPresent()) {

        }

        try {
            Claims claims = parseToken(jwt.get());

            // Set the authentication context
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(buildAuthenticationToken(claims));
        } catch (Exception e) {
            // Log token parsing or authentication failures (optional)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT token");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return Optional.empty();
        }
        return Optional.of(header.substring("Bearer ".length()));
    }

    private Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JwtTokenProvider.key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private UsernamePasswordAuthenticationToken buildAuthenticationToken(Claims claims) {
        return new UsernamePasswordAuthenticationToken(claims, "claims",
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

}
