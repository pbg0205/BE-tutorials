package com.cooper.woowaresttutorial.person;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private Person(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public static Person create(String firstName, String lastName, LocalDate birthDate) {
        return new Person(firstName, lastName, birthDate);
    }

    public void update(PersonUpdateDto personUpdateDto) {
        this.firstName = personUpdateDto.getFirstName();
        this.lastName = personUpdateDto.getLastName();
        this.birthDate = personUpdateDto.getBirthDate();
    }

}
