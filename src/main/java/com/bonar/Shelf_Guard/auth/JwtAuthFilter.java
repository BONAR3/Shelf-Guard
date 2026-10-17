package com.bonar.Shelf_Guard.auth;

import com.bonar.Shelf_Guard.exceptions.ErrorResponseWriter;
import com.bonar.Shelf_Guard.service.LogInSessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UsersDetailService usersDetailService;
    private final LogInSessionService loginSessionService;
    private final ErrorResponseWriter errorResponseWriter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            TokenClaims claims = jwtService.extractTokenClaims(token);

            if (claims.getAccountId() == null || claims.getSessionId() == null) {
                rejectRequest(response, "Invalid token");
                return;
            }

            UserDetails userDetails = usersDetailService.loadUserByUsername(claims.getEmail());
            if (!jwtService.validateToken(token, userDetails)) {
                rejectRequest(response, "Invalid token");
                return;
            }
            if (!userDetails.isEnabled() || !userDetails.isAccountNonLocked()) {
                errorResponseWriter.write(response, HttpStatus.FORBIDDEN, "Account disabled");
                return;
            }

            boolean sessionStillActive = loginSessionService.isCurrentSession(
                    UUID.fromString(claims.getAccountId()), claims.getSessionId());
            if (!sessionStillActive) {
                rejectRequest(response, "Session no longer active");
                return;
            }
            loginSessionService.extendSession(
                    UUID.fromString(claims.getAccountId()), claims.getSessionId());

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (ExpiredJwtException ex) {
            rejectRequest(response, "Token expired");
        } catch (UsernameNotFoundException ex) {
            rejectRequest(response, "Invalid token");
        } catch (JwtException ex) {
            logger.error("Unexpected error while authenticating request", ex);
            errorResponseWriter.write(response, HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void rejectRequest(HttpServletResponse response, String message) throws IOException {
        errorResponseWriter.write(response, HttpStatus.UNAUTHORIZED, message);
    }
}
