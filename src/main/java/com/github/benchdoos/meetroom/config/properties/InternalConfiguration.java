package com.github.benchdoos.meetroom.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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

        /**
         * Account activation request lives for count of days:
         */
        @Min(1)
        @Max(30)
        private short accountActivationExpiresInDays;

        /**
         * Reset password request lives for count of days:
         */
        @Min(1)
        @Max(30)
        private short resetPasswordExpiresInDays;

        /**
         * User email update request lives for count of days:
         */
        @Min(1)
        @Max(30)
        private short emailUpdateRequestExpiresInDays;
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
        @NotBlank
        private String resetPasswordEmailMessage;

        @NotBlank
        private String accountActivationEmailMessage;

        @NotBlank
        private String userEmailUpdateOldMessage;

        @NotBlank
        private String userEmailUpdateNewMessage;
    }
}
