package com.cooper.tobyboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

public class HelloBootApplication {

    public static void main(String[] args) {
        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        WebServer webServer = serverFactory.getWebServer(new ServletContextInitializer() {
            HelloController helloController = new HelloController();

            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                servletContext.addServlet("frontcontroller", new HttpServlet() {
                    @Override
                    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                        // 인증, 보안, 다국어 처리

                        if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
                            String name = req.getParameter("name");
                            String returnBody = helloController.hello(name);
                            resp.setStatus(HttpStatus.OK.value());
                            resp.setHeader(HttpHeaders.CONTENT_TYPE, TEXT_PLAIN_VALUE);
                            resp.getWriter().print(returnBody);
                        } else if (req.getRequestURI().equals("/users")) {
                            // 요청 처리
                        } else {
                            resp.setStatus(HttpStatus.NOT_FOUND.value());

                        }

                    }
                }).addMapping("/*");
            }
        });
        webServer.start();
    }

}
