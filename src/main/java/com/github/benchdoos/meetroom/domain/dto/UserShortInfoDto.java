package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Dto with important public personal information for {@link User}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserShortInfoDto {
    @NotNull
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

}
