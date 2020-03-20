package com.github.benchdoos.meetroom.domain.annotations.validators;

import com.github.benchdoos.meetroom.domain.annotations.PrivilegeOrRoleName;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PrivilegeOrRoleNameValidator implements ConstraintValidator<PrivilegeOrRoleName, String> {
   private static final String SUPPORTED_SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_:";
   private static final int MAX_NAME_LENGHT = 256;

   public void initialize(PrivilegeOrRoleName constraint) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {

       return StringUtils.hasText(value)
               && value.length() <= MAX_NAME_LENGHT
               && validValue(value)
               && validSymbol(value.substring(0, 1))
               && validSymbol(value.substring(value.length() - 1));
    }

   private boolean validValue(String value) {
      for (char c : value.toCharArray()) {
         if (!SUPPORTED_SYMBOLS.contains(Character.toString(c))) {
            return false;
         }
      }
      return true;
   }

   private boolean validSymbol(String substring) {
      return !substring.contains("_") && !substring.contains(":");
   }

}
