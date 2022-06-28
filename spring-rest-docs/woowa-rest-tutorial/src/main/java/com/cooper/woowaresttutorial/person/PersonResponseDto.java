package com.cooper.woowaresttutorial.person;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonResponseDto {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;
    private final Gender gender;

    public static PersonResponseDto fromEntity(Person person) {
        return new PersonResponseDto(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getBirthDate(),
                person.getGender()
        );
    }
}
