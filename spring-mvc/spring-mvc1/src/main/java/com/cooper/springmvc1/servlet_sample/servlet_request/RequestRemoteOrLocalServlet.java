package com.cooper.springmvc1.servlet_sample.servlet_request;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebServlet(name = "requestRemoteOrLocalServlet", urlPatterns = "request-remote-local")
public class RequestRemoteOrLocalServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        infoRemote(request);
        infoLocal(request);
    }

    private void infoRemote(HttpServletRequest request) {
        log.info("[Remote 정보]");
        log.info("{} : {}", "request.getRemoteHost()", request.getRemoteHost());
        log.info("{} : {}", "request.getRemoteAddr()", request.getRemoteAddr());
        log.info("{} : {}", "request.getRemotePort()", request.getRemotePort());
    }

    private void infoLocal(HttpServletRequest request) {
        log.info("[Local 정보]");
        log.info("{} : {}", "request.getLocalName()", request.getLocalName());
        log.info("{} : {}", "request.getLocalAddr()", request.getLocalAddr());
        log.info("{} : {}", "request.getLocalPort()", request.getLocalPort());
    }

}
