package com.vahana.middlewares;

import com.vahana.configurations.logging.CustomMDCTypes;
import com.vahana.configurations.logging.TargetTypes;
import com.vahana.repositories.v1.users.UserRepository;
import com.vahana.services.v1.auth.JwtService;
import com.vahana.utils.v1.MDCUtils;
import com.vahana.utils.v1.auth.AuthType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Arrays;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
@RequiredArgsConstructor
public final class AuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    private static final String AUTH_HEADER_PREFIX = "Bearer ";
    private static final String COOKIE_NAME = "jwt";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            MDCUtils.configureMDCVar(CustomMDCTypes.TARGET, TargetTypes.AUTH);
            var token = extractToken(request);

            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            if (jwtService.extractTokenType(token) != AuthType.ACCESS) {
                filterChain.doFilter(request, response);
                return;
            }

            String userEmail = jwtService.extractUsername(token);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException(userEmail));

                if (jwtService.isTokenValid(token, user)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.error("Error on Auth occurred: {}", ex.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }

    private String extractToken(HttpServletRequest request) {
        // Check Cookies first
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> COOKIE_NAME.equals(cookie.getName()))
                    .findFirst()
                    .map(cookie -> {
                        String value = cookie.getValue();
                        return value.startsWith(AUTH_HEADER_PREFIX)
                                ? value.substring(AUTH_HEADER_PREFIX.length())
                                : value;
                    })
                    .orElse(null);
        }

        // Check Authorization Header
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith(AUTH_HEADER_PREFIX)) {
            return authHeader.substring(AUTH_HEADER_PREFIX.length());
        }

        return null;
    }
}
