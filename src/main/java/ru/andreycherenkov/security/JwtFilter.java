package ru.andreycherenkov.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@AllArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";

    private final AuthUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        var authHeader = request.getHeader(AUTH_HEADER);
        String jwt = null;
        String phoneNumber = null;
        if (Objects.nonNull(authHeader) &&
                authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            phoneNumber = jwtService.extractPhoneNumber(jwt);
        }

        if (Objects.nonNull(phoneNumber) &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userDetailsService.loadUserByUsername(phoneNumber);
            var isTokenValidated = jwtService.validateToken(jwt, userDetails);
            if (isTokenValidated) {
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
