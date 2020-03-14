package com.github.benchdoos.meetroom.domain.annotations;

import com.github.benchdoos.meetroom.domain.annotations.validators.PrivilegeOrRoleNameValidator;

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
 * Privilege or role name annotation checker.
 * Name must be upper case with no spaces, and in English, without numbers.
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PrivilegeOrRoleNameValidator.class)
@Documented
public @interface PrivilegeOrRoleName {
    String message() default "Invalid role or privilege name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
