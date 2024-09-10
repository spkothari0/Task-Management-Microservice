package com.shreyas.filter.correlation;

import com.shreyas.AppConstant;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
//@Order(1)
public class CorrelationIdFilter implements Filter {

    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/swagger-ui/.*",
            "/api-docs.*",
            "/swagger-resources.*",
            "/swagger-ui.html.*",
            "/webjars/.*",
            "/api/v1/user/verification/.*");

    private static List<Pattern> compilePatterns(List<String> paths) {
        return paths.stream()
                .map(Pattern::compile)
                .toList();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String path = httpServletRequest.getRequestURI();
        List<Pattern> EXCLUDED_PATTERNS = compilePatterns(EXCLUDED_PATHS);

        boolean isExcluded = EXCLUDED_PATTERNS.stream().anyMatch(pattern -> pattern.matcher(path).matches());

        if (isExcluded) {
            filterChain.doFilter(request, response);
            return;
        }
        String correlationId = httpServletRequest.getHeader(AppConstant.CORRELATION_ID);
        if (correlationId == null || !isValidUUID(correlationId)) {
            throw new IllegalArgumentException("Correlation header is missing");
        }

        MDC.put(AppConstant.CORRELATION_ID, correlationId);
        httpServletResponse.setHeader(AppConstant.CORRELATION_ID, correlationId);
        try{
            filterChain.doFilter(request,response);
        }finally {
            MDC.remove(AppConstant.CORRELATION_ID);
        }
    }

    private boolean isValidUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
