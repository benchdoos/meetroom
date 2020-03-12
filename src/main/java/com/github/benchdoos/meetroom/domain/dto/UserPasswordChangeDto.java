package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.PasswordConfirmation;
import com.github.benchdoos.meetroom.domain.annotations.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@PasswordMatches
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserPasswordChangeDto implements PasswordConfirmation {

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

}
