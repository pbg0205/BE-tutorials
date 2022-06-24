package com.cooper.springmvc1.servlet_sample.servlet_request;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        infoStartLine(request);
        infoHeaders(request);
        infoHeaderUtils(request);
    }

    private void infoStartLine(HttpServletRequest request) {
        log.info("--- REQUEST-LINE - start ---");

        log.info("{} : {}", "request.getMethod()", request.getMethod()); // GET
        log.info("{} : {}", "request.getProtocol()", request.getProtocol()); // HTTP/1.1
        log.info("{} : {}", "request.getScheme()", request.getScheme()); // http
        log.info("{} : {}", "request.getRequestURL()", request.getRequestURL()); // http://localhost:8080/request-header
        log.info("{} : {}", "request.getRequestURI()", request.getRequestURI()); // /request-header
        log.info("{} : {}", "request.getQueryString()", request.getQueryString()); //username=hi
        log.info("{} : {}", "request.isSecure()", request.isSecure()); //https 사용 유무

        log.info("--- REQUEST-LINE - end ---");
    }

    private void infoHeaders(HttpServletRequest request) {
        log.info("--- Headers - start ---");

        request.getHeaderNames().asIterator()
                .forEachRemaining(headerName -> log.info("{} : {}", headerName, request.getHeader(headerName)));

        log.info("--- Headers - end ---");
    }

    private void infoHeaderUtils(HttpServletRequest request) {
        log.info("--- Header 편의 조회 start ---");

        log.info("[Host 편의 조회]");
        log.info("{} : {} ", "request.getServerName() = ", request.getServerName()); //Host 헤더
        log.info("{} : {} ", "request.getServerPort() = ", request.getServerPort()); //Host 헤더

        log.info("[Accept-Language 편의 조회]");
        request.getLocales().asIterator().forEachRemaining(locale -> log.info("locale = " + locale));
        log.info("{} : {}", "request.getLocale()", request.getLocale());

        log.info("[cookie 편의 조회]");
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                log.info("{} : {}", cookie.getName(), cookie.getValue());
            }
        }

        log.info("[Content 편의 조회]");
        log.info("{} : {}", "request.getContentType()", request.getContentType());
        log.info("{} : {}", "request.getContentLength()", request.getContentLength());
        log.info("{} : {}", "request.getCharacterEncoding()", request.getCharacterEncoding());
        log.info("--- Header 편의 조회 end ---");
    }
}
