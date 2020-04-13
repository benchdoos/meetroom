package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.annotations.Username;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Dto to edit {@link User#getUsername()}
 */
@RequiredArgsConstructor
@Getter
@ToString
public class EditUserUsernameDto {

    @Username
    private final String oldUsername;

    @Username
    private final String newUsername;
}
