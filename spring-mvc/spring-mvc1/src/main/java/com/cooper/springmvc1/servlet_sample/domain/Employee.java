package com.cooper.springmvc1.servlet_sample.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Employee {

    private final String username;
    private final int age;

    public static Employee create(String username, int age) {
        return new Employee(username, age);
    }

}
