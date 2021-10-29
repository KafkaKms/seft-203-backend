package com.kms.seft203.auth;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("NullableProblems")
@Log4j2
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final UserJwtRepository userJwtRepository;

    public AuthTokenFilter(JwtService jwtService, UserRepository userRepository, UserJwtRepository userJwtRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.userJwtRepository = userJwtRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        var token = header.split(" ")[1].trim();
        var username = jwtService.decodeJwtToken(token).getClaim("username").asString();
        var user = userRepository.findByUsername(username).orElse(null);

        if (ObjectUtils.isEmpty(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid access token");
        }

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        user,
                        token,
                        null
                )
        );

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        var path = request.getRequestURI();
        return path.contains("/auth/login") || path.contains("/auth/register") || path.contains("/auth/refresh");
    }
}
