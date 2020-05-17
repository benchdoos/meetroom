package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.annotations.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto to update user email
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateUserEmailDto {

    @Email
    private String newEmail;
}
