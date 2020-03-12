package com.github.benchdoos.meetroom.domain.annotations.validators;

import com.github.benchdoos.meetroom.domain.PasswordConfirmation;
import com.github.benchdoos.meetroom.domain.annotations.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, PasswordConfirmation> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(PasswordConfirmation dto, ConstraintValidatorContext context) {
        return dto.getPassword().equals(dto.getConfirmPassword());
    }
}
