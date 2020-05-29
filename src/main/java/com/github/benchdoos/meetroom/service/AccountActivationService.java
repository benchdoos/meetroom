package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.AccountActivationRequest;
import com.github.benchdoos.meetroom.domain.User;

import java.util.UUID;

/**
 * Service that provides account activation processes
 */
public interface AccountActivationService {

    /**
     * Create account activation for user
     *
     * @param user user that's needed to be activated
     * @return request
     */
    AccountActivationRequest createAccountActivationRequest(User user);

    /**
     * Activate account by activation request id
     *
     * @param requestId id of request
     */
    void activateAccount(UUID requestId);
}
