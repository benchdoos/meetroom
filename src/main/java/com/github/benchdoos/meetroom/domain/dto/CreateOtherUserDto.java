package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.annotations.Email;
import com.github.benchdoos.meetroom.domain.annotations.Username;
import com.github.benchdoos.meetroom.domain.interfaces.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Dto to create other user without password and disabled until activation.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateOtherUserDto implements UserInfo {

    @NotBlank
    @Username
    private String username;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

}
