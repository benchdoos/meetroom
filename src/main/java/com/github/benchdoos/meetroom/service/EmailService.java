package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.PasswordResetRequest;
import com.github.benchdoos.meetroom.domain.User;

import javax.mail.MessagingException;

/**
 * Service that provides sending emails to users
 */
public interface EmailService {

    /**
     * Send user account password reset notification email
     *
     * @param user user to notify
     * @param passwordResetRequest password reset request
     */
    void sendResetPasswordNotification(User user, PasswordResetRequest passwordResetRequest) throws MessagingException;

    /**
     * Send account activation email
     *
     * @param user user to notify
     */
    void sendAccountActivation(User user);
}
