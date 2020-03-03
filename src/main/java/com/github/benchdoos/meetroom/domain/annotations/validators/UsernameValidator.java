package com.github.benchdoos.meetroom.domain.annotations.validators;

import com.github.benchdoos.meetroom.domain.annotations.Username;
import com.google.common.base.CharMatcher;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {

    public void initialize(Username constraint) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.hasText(value)
                && !value.contains(" ")
                && CharMatcher.javaLowerCase().matchesAllOf(value);
    }
}
