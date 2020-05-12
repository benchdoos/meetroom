package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.annotations.Username;
import com.github.benchdoos.meetroom.domain.interfaces.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Dto to edit user info by admins in manage users page
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EditOtherUserDto implements UserInfo {

    @Username
    @NotNull
    private String username;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;
}
