package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.annotations.PasswordMatches;
import com.github.benchdoos.meetroom.domain.annotations.Username;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Dto to create {@link User}
 */
@PasswordMatches
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateUserDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Username
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;
}
