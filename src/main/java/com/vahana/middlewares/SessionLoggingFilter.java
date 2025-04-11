package com.vahana.middlewares;

import com.vahana.configurations.logging.CustomMDCTypes;
import com.vahana.configurations.logging.TargetTypes;
import com.vahana.utils.v1.MDCUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public final class SessionLoggingFilter extends OncePerRequestFilter {
    private static final String REQUEST_ID_HEADER = "X-Request-ID";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            MDCUtils.configureMDCVar(CustomMDCTypes.TARGET, TargetTypes.SESSION);
            var requestId = request.getHeader(REQUEST_ID_HEADER);
            if (requestId == null || requestId.isBlank()) {
                requestId = UUID.randomUUID().toString();
            }

            MDCUtils.configureMDCVar(CustomMDCTypes.REQUEST_ID, requestId);
            var session = request.getSession(false);
            var sessionId = session != null ? session.getId() : "no-session";
            var requestURI = request.getRequestURI();
            var method = request.getMethod();
            log.info("Method={}, URI={}, SessionID={}", method, requestURI, sessionId);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
        }
    }
}
