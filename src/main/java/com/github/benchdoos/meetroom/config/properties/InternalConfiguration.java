package com.github.benchdoos.meetroom.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Properties for meeting rooms
 */
@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("internal-configuration")
public class InternalConfiguration {

    /**
     * Configuration for user endpoints. Defaults and settings
     */
    @NotNull
    private UserSettings userSettings;

    @NotNull
    private EmailSettings emailSettings;

    @Getter
    @Setter
    @Validated
    public static class UserSettings {

        /**
         * User's avatar square side
         */
        @NotNull
        private int avatarSize;

        /**
         * Default user avatar if user has no avatar
         */
        @NotNull
        private String defaultAvatarId;
    }

    /**
     * Email settings and messages
     */
    @Getter
    @Setter
    @Validated
    public static class EmailSettings {
        /**
         * Message for resetting password
         */
        @NotNull
        private String resetPasswordEmailMessage;
    }
}
