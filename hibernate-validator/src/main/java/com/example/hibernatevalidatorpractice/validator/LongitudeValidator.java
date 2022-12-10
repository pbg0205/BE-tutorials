package com.example.hibernatevalidatorpractice.validator;

import com.example.hibernatevalidatorpractice.annotation.Longitude;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LongitudeValidator implements ConstraintValidator<Longitude, Double> {

    private static final double MAX_LONGITUDE_RANGE = 180.0;
    private static final double MIN_LONGITUDE_RANGE = -180.0;

    @Override
    public void initialize(Longitude constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if(ObjectUtils.isEmpty(value)) {
            return false;
        }

        if ((value > MAX_LONGITUDE_RANGE) || (value < MIN_LONGITUDE_RANGE)) {
            return false;
        }

        return true;
    }

}
