package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.annotations.PasswordMatches;
import com.github.benchdoos.meetroom.domain.interfaces.PasswordConfirmation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Dto to reset user's password
 */
@PasswordMatches
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResetUserPasswordDto implements PasswordConfirmation {
    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;
}
