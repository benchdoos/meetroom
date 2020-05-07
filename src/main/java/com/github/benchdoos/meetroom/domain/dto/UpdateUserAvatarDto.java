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
 * Dto to update user {@link Avatar}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateUserAvatarDto {

    /**
     * Avatar data type
     */
    @NotNull
    private AvatarDataType type;

    /**
     * Avatar data. In case of {@link AvatarDataType#BASE64} must contain base64 ready string.
     * In case of {@link AvatarDataType#GRAVATAR} must contain only email address.
     */
    @NotBlank
    private String data;
}
