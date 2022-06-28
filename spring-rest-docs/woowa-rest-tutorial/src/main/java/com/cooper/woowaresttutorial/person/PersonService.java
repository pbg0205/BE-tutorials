package com.cooper.woowaresttutorial.person;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public PersonResponseDto update(Long id, PersonUpdateDto personUpdateDto) {
        Person person = personRepository.findById(id).orElseThrow(RuntimeException::new);
        person.update(personUpdateDto);
        return PersonResponseDto.fromEntity(person);
    }
}
