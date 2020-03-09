package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.UserInfo;
import com.github.benchdoos.meetroom.domain.annotations.Username;
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

}
