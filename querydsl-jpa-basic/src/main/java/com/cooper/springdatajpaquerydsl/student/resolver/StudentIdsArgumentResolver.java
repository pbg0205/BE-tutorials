package com.cooper.springdatajpaquerydsl.student.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

public class StudentIdsArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String STUDENT_IDS = "studentIds";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterName().equals("studentIds")
                && parameter.hasParameterAnnotation(RequestParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String[] split = ((String) httpServletRequest.getAttribute(STUDENT_IDS)).split(",");

        return Arrays.stream(split).map(input -> Long.valueOf(input.trim()))
                .collect(Collectors.toList());
    }
}
