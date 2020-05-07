package com.github.benchdoos.meetroom.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateUserInfoDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}
