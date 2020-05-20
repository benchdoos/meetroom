package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.AccountActivationRequest;
import com.github.benchdoos.meetroom.domain.PasswordResetRequest;
import com.github.benchdoos.meetroom.domain.UserEmailUpdateRequest;

/**
 * Service that provides sending emails to users
 */
//todo find method to avoid throwing publicFullApplicationUrl through parameters (mby without ServletRequest)
public interface EmailService {

    /**
     * Send user account password reset notification email
     *  @param publicFullApplicationUrl full public url for application
     * @param passwordResetRequest password reset request
     */
    void sendResetPasswordNotification(String publicFullApplicationUrl, PasswordResetRequest passwordResetRequest);

    /**
     * Send account activation email
     *
     * @param publicFullApplicationUrl full public url for application
     * @param accountActivationRequest account activation request
     */
    void sendAccountActivation(String publicFullApplicationUrl, AccountActivationRequest accountActivationRequest);

    /**
     * Send email update messages. Given emails will have links to submit user email update request
     *  @param publicFullApplicationUrl full public url for application
     * @param oldEmail old user email
     * @param newEmail new user email
     * @param emailUpdateRequest request
     */
    void sendEmailUpdateRequests(String publicFullApplicationUrl, String oldEmail, String newEmail, UserEmailUpdateRequest emailUpdateRequest);
}
