package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.PasswordResetRequest;
import com.github.benchdoos.meetroom.domain.User;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface PasswordResetRequestService {

    /**
     * Get {@link PasswordResetRequest} or throws exception
     *
     * @param id request id
     * @return request by id
     */
    PasswordResetRequest getById(UUID id);

    /**
     * Create password reset request
     *
     * @param byUsername user who requested password reset
     * @param user user that needs password reset
     * @param requestTime time of request
     * @return request
     */
    PasswordResetRequest createPasswordResetRequest(User byUsername, User user, ZonedDateTime requestTime);

    /**
     * Deactivate given passwordResetRequest
     *
     * @param passwordResetRequest to deactivate
     */
    void deactivateRequest(PasswordResetRequest passwordResetRequest);
}
