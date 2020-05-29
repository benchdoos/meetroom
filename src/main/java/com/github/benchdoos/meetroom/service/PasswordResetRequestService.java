package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.PasswordResetRequest;

import java.util.UUID;

public interface PasswordResetRequestService {

    /**
     * Get {@link PasswordResetRequest} or throws exception
     *
     * @param id request id
     * @return request by id
     */
    PasswordResetRequest getById(UUID id);
}
