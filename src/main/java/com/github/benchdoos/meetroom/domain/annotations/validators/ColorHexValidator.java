package com.github.benchdoos.meetroom.domain.annotations.validators;

import com.github.benchdoos.meetroom.domain.annotations.ColorHex;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorHexValidator implements ConstraintValidator<ColorHex, String> {
    private static final String HEX_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
    private Pattern pattern = Pattern.compile(HEX_PATTERN);


    public void initialize(ColorHex constraint) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.hasText(value)) {
            final Matcher matcher = pattern.matcher(value);

            return matcher.matches();
        }
        //no value = valid for this business case
        return true;
    }
}