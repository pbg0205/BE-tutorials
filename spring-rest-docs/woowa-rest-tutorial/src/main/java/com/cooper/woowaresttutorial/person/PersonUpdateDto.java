package com.cooper.woowaresttutorial.person;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonUpdateDto {

    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;
    private final Gender gender;

    public static PersonUpdateDto create(String firstName, String lastName, LocalDate birthDate, Gender gender) {
        return new PersonUpdateDto(firstName, lastName, birthDate, gender);
    }

}
