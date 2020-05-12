package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.domain.AccountActivationRequest;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.exceptions.AccountActivationRequestExpiredException;
import com.github.benchdoos.meetroom.exceptions.AccountActivationRequestIsNotActiveAnyMoreException;
import com.github.benchdoos.meetroom.exceptions.AccountActivationRequestNotFoundException;
import com.github.benchdoos.meetroom.repository.AccountActivationRequestRepository;
import com.github.benchdoos.meetroom.repository.UserRepository;
import com.github.benchdoos.meetroom.service.AccountActivationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccountActivationServiceImpl implements AccountActivationService {
    private final AccountActivationRequestRepository activationRequestRepository;
    private final UserRepository userRepository;

    @Override
    public AccountActivationRequest createAccountActivationRequest(User user) {
        final ZonedDateTime requested = ZonedDateTime.now();

        disablePreviousRequests(user, requested);

        final AccountActivationRequest accountActivationRequest = AccountActivationRequest.builder()
                .requestedFor(user)
                .requested(requested)
                .expires(requested.plusDays(1))
                .active(true)
                .build();

        return activationRequestRepository.save(accountActivationRequest);
    }

    @Override
    public void activateAccount(UUID requestId) {
        final AccountActivationRequest accountActivationRequest =
                activationRequestRepository.findById(requestId).orElseThrow(AccountActivationRequestNotFoundException::new);
        if (!accountActivationRequest.isActive()) {
            throw new AccountActivationRequestIsNotActiveAnyMoreException();
        }

        if (ZonedDateTime.now().isAfter(accountActivationRequest.getExpires())) {
            throw new AccountActivationRequestExpiredException();
        }

        @NotNull final User requestedFor = accountActivationRequest.getRequestedFor();
        requestedFor.setNeedActivation(false);
        userRepository.save(requestedFor);


        accountActivationRequest.setActive(false);
        activationRequestRepository.save(accountActivationRequest);
    }

    /**
     * Disable all previously created requests for user and time
     *
     * @param user user
     * @param requested time
     */
    private void disablePreviousRequests(User user, ZonedDateTime requested) {
        final Collection<AccountActivationRequest> presentRequests =
                activationRequestRepository.findByRequestedForAndExpiresIsAfterAndActiveIsTrue(user, requested);

        if (!CollectionUtils.isEmpty(presentRequests)) {
            presentRequests.forEach(request -> request.setActive(false));
            activationRequestRepository.saveAll(presentRequests);
        }
    }
}
