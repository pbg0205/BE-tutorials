package com.example.hibernatevalidatorpractice.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        for (ConstraintViolation<?> constraintViolation : constraintViolationException.getConstraintViolations()) {
            System.out.println("--------------");
            System.out.println("constraintViolation.getMessage() = " + constraintViolation.getMessage());
            System.out.println("constraintViolation.getMessageTemplate() = " + constraintViolation.getMessageTemplate());
            System.out.println("constraintViolation.getConstraintDescriptor() = " + constraintViolation.getConstraintDescriptor());
            System.out.println("constraintViolation.getExecutableParameters() = " + constraintViolation.getExecutableParameters());
            System.out.println("constraintViolation.getExecutableReturnValue() = " + constraintViolation.getExecutableReturnValue());
            System.out.println("constraintViolation.getInvalidValue() = " + constraintViolation.getInvalidValue());
            System.out.println("--------------");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(constraintViolationException.getMessage());
    }

}
