package com.cooper.springweb.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Slf4j
@Component
@Order(2)
public class SecondGeneralFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Start FirstGeneralFilter");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        log.info("Request URI: {}", req.getRequestURL());

        chain.doFilter(request, response);

        log.info("Return URI: {}", req.getRequestURL());

    }

    @Override
    public void destroy() {
        log.info("End FirstGeneralFilter");
        Filter.super.destroy();
    }

}
