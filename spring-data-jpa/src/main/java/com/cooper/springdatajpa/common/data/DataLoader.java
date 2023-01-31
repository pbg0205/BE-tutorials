package com.cooper.springdatajpa.common.data;

import com.cooper.springdatajpa.student.domain.Student;
import com.cooper.springdatajpa.student.domain.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final StudentRepository studentRepository;

    @Transactional
    public void load() {

        for (int number = 0; number < 10_000; number++) {
            studentRepository.save(new Student("멤버" + number, String.valueOf(number % 100)));
        }
    }
}
