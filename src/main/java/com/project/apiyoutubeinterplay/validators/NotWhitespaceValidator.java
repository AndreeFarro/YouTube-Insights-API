package com.project.apiyoutubeinterplay.validators;

import com.project.apiyoutubeinterplay.validators.annotation.NotWhitespace;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class NotWhitespaceValidator implements ConstraintValidator<NotWhitespace,String> {
    @Override
    public void initialize(NotWhitespace constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value.trim().equals(value);
    }
}
