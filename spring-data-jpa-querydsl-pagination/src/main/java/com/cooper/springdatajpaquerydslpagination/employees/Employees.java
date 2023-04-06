package com.cooper.springdatajpaquerydslpagination.employees;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employees {

    @Id
    @Column(name = "emp_no")
    private Long id;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime hireDate;

    public Employees(String firstName, String lastName, Gender gender, LocalDateTime birthDate, LocalDateTime hireDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
    }

}
