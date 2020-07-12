package com.github.benchdoos.meetroom.domain.annotations;

import com.github.benchdoos.meetroom.domain.annotations.validators.AvatarValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = AvatarValidator.class)
@Documented
public @interface ValidAvatar {
    String message() default "Avatar data is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
