package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.UserEmailUpdateRequest;

import java.util.UUID;

/**
 * Service to operate with email updates
 */
public interface UserEmailUpdateService {

    /**
     * Create new request to update user email. User must confirm both emails to make it proceed.
     *
     * @param user user requested for
     * @param newEmailAddress new address
     * @return request
     */
    UserEmailUpdateRequest createEmailUpdateRequest(User user, String newEmailAddress);

    /**
     * Submit email change by {@link UserEmailUpdateRequest#getOldEmailConfirmation()} or
     * by {@link UserEmailUpdateRequest#getNewEmailAddress()}. When both emails are submitted, user email will be updated
     * to given email.
     *
     * @param emailId id of email to submit
     */
    UserEmailUpdateRequest submitEmailRequest(UUID emailId);
}
