package com.github.benchdoos.meetroom.domain.annotations;

import com.github.benchdoos.meetroom.domain.annotations.validators.ColorHexValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to check, is string a color hex value
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ColorHexValidator.class)
@Documented
public @interface ColorHex {
    String message() default "Invalid hex color";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
