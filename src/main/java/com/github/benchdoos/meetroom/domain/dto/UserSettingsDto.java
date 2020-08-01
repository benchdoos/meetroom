package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.UserSettings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto-projection for {@link UserSettings}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserSettingsDto {

    /**
     * Show user email to other users on user page. Do not effect on admin users.
     */
    private Boolean showEmailToOtherUsers = true;

    /**
     * Returns true if dark mode is enabled for user
     */
    private Boolean darkModeEnabled = false;

}
