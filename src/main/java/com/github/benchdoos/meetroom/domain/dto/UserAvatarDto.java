package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.Avatar;
import com.github.benchdoos.meetroom.domain.enumirations.AvatarDataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Dto with {@link Avatar} information
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserAvatarDto {

    /**
     * Avatar type
     */
    @NotNull
    private AvatarDataType type;

    /**
     * Data of given avatar transformed to integrate with frontend. Can be used in {@code src} attribute.
     */
    @NotBlank
    private String src;
}
