package com.cooper.woowaresttutorial.person;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<PersonResponseDto> update(@PathVariable Long id, @RequestBody PersonUpdateDto personUpdateDto) {
        PersonResponseDto personResponseDto = personService.update(id, personUpdateDto);
        return ResponseEntity.ok().body(personResponseDto);
    }

}
