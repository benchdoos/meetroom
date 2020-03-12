package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.interfaces.UserInfo;
import com.github.benchdoos.meetroom.domain.annotations.Username;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
}
