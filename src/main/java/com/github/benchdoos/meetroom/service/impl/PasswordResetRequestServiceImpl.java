package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.config.properties.InternalConfiguration;
import com.github.benchdoos.meetroom.domain.PasswordResetRequest;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.exceptions.PasswordResetRequestExpiredException;
import com.github.benchdoos.meetroom.exceptions.PasswordResetRequestIsNotActiveAnyMoreException;
import com.github.benchdoos.meetroom.exceptions.PasswordResetRequestNotFoundException;
import com.github.benchdoos.meetroom.repository.PasswordResetRequestRepository;
import com.github.benchdoos.meetroom.service.PasswordResetRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PasswordResetRequestServiceImpl implements PasswordResetRequestService {
    private final PasswordResetRequestRepository resetRequestRepository;
    private final InternalConfiguration internalConfiguration;

    @Override
    public PasswordResetRequest getById(UUID id) {
        final PasswordResetRequest passwordResetRequest = resetRequestRepository.findById(id)
                .orElseThrow(PasswordResetRequestNotFoundException::new);
        if (!passwordResetRequest.isActive()) {
            throw new PasswordResetRequestIsNotActiveAnyMoreException();
        }

        if (ZonedDateTime.now().isAfter(passwordResetRequest.getExpires())) {
            throw new PasswordResetRequestExpiredException();
        }

        return passwordResetRequest;
    }

    @Override
    public PasswordResetRequest createPasswordResetRequest(User byUsername, User user, ZonedDateTime requestTime) {
        final Collection<PasswordResetRequest> allActivePasswordResetRequests =
                resetRequestRepository.findByRequestedForAndExpiresIsAfterAndActiveIsTrue(user, requestTime);

        if (!CollectionUtils.isEmpty(allActivePasswordResetRequests)) {
            allActivePasswordResetRequests.forEach(passwordResetRequest -> passwordResetRequest.setActive(false));
            resetRequestRepository.saveAll(allActivePasswordResetRequests);
        }

        final PasswordResetRequest passwordResetRequest = PasswordResetRequest.builder()
                .requestedBy(byUsername)
                .requestedFor(user)
                .active(true)
                .requested(requestTime)
                .expires(requestTime.plusDays(internalConfiguration.getUserSettings().getResetPasswordExpiresInDays()))
                .build();

        return resetRequestRepository.save(passwordResetRequest);
    }

    @Override
    public void deactivateRequest(PasswordResetRequest passwordResetRequest) {
        passwordResetRequest.setActive(false);
        resetRequestRepository.save(passwordResetRequest);
    }
}
