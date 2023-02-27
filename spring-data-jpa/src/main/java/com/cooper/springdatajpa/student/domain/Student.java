package com.cooper.springdatajpa.student.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String tagName;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Award> awards = new ArrayList<>();

    public Student(String name, String tagName) {
        this.name = name;
        this.tagName = tagName;
    }

    public Student(Long id, String name, String tagName) {
        this.id = id;
        this.name = name;
        this.tagName = tagName;
    }

    public void addAward(Award award) {
        this.awards.add(award);
        award.setStudent(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
