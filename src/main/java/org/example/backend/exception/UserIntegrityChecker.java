package org.example.backend.exception;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import io.jsonwebtoken.Claims;

public final class UserIntegrityChecker {
    public static void isIdValid(long userId) throws AuthenticationException {
        Claims principal = (Claims) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer authenticatedUserId = principal.get("id", Double.class).intValue();

        if (userId != authenticatedUserId) {
            throw new AuthenticationException("Id does not match!");
        }
    }

    private UserIntegrityChecker() {
    }
}
