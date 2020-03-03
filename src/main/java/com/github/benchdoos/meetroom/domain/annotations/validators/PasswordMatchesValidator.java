package com.github.benchdoos.meetroom.domain.annotations.validators;

import com.github.benchdoos.meetroom.domain.annotations.PasswordMatches;
import com.github.benchdoos.meetroom.domain.dto.CreateUserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, CreateUserDto> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(CreateUserDto userDto, ConstraintValidatorContext context) {
        return userDto.getPassword().equals(userDto.getConfirmPassword());
    }
}
