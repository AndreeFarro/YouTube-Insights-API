package com.project.apiyoutubeinterplay.validators.annotation;

import com.project.apiyoutubeinterplay.validators.NotWhitespaceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotWhitespaceValidator.class)
public @interface NotWhitespace {
    String message() default "No se Permiten espacios";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
