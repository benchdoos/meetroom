package com.github.benchdoos.meetroom.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto to update user settings
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateUserSettingsDto {

    /**
     * Show user email to other users on user page. Do not effect on admin users.
     */
    private Boolean showEmailToOtherUsers;

    /**
     * Returns true if dark mode is enabled for user
     */
    private Boolean darkModeEnabled;
}
