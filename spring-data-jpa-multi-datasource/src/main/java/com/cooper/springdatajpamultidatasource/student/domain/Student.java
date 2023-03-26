package com.cooper.springdatajpamultidatasource.student.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "class_number", length = 20)
    private int classNumber;

    public Student(String name, int classNumber) {
        this.name = name;
        this.classNumber = classNumber;
    }

    public void update(String name, int classNumber) {
        this.name = name;
        this.classNumber = classNumber;
    }

}
