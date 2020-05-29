package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.annotations.Email;
import com.github.benchdoos.meetroom.domain.annotations.PasswordMatches;
import com.github.benchdoos.meetroom.domain.annotations.Username;
import com.github.benchdoos.meetroom.domain.interfaces.PasswordConfirmation;
import com.github.benchdoos.meetroom.domain.interfaces.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Dto to create {@link User}
 */
@PasswordMatches
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateUserDto implements UserInfo, PasswordConfirmation {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Username
    @NotBlank
    @Size(min = 4, max = 16)
    private String username;

    @Email
    @NotBlank
    @Size(min = 4, max = 320)
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;
}
