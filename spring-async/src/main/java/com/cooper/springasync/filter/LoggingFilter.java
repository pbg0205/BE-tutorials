package com.cooper.springasync.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class LoggingFilter implements Filter {

    private static final String TRACE_ID = "traceId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String traceId = UUID.randomUUID().toString();
        MDC.put(TRACE_ID, traceId);

        chain.doFilter(request, response);

        MDC.clear();
    }

}
