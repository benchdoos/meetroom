package com.github.benchdoos.meetroom.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Dto to update user's enable-ability
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EditUserEnableDto {

    @NotNull
    private Boolean enabled;
}
