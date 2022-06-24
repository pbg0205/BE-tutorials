package com.cooper.springmvc1.servlet_sample.servlet_request;

import com.cooper.springmvc1.servlet_sample.domain.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    /*
     * 스프링에서는 기본적으로 jackson library 를 제공한다.
     * - com.fasterxml.jackson.databind
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        Employee employee = objectMapper.readValue(messageBody, Employee.class);

        log.info("{} : {}", "employee.username", employee.getUsername());
        log.info("{} : {}", "employee.age", employee.getAge());
    }

}
