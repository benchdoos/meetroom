package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.domain.PasswordResetRequest;
import com.github.benchdoos.meetroom.exceptions.PasswordResetRequestExpired;
import com.github.benchdoos.meetroom.exceptions.PasswordResetRequestIsNotActiveAnyMore;
import com.github.benchdoos.meetroom.exceptions.PasswordResetRequestNotFoundException;
import com.github.benchdoos.meetroom.repository.PasswordResetRequestRepository;
import com.github.benchdoos.meetroom.service.PasswordResetRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PasswordResetRequestServiceImpl implements PasswordResetRequestService {
    private final PasswordResetRequestRepository resetRequestRepository;

    @Override
    public PasswordResetRequest getById(UUID id) {
        final PasswordResetRequest passwordResetRequest = resetRequestRepository.findById(id)
                .orElseThrow(PasswordResetRequestNotFoundException::new);
        if (!passwordResetRequest.isActive()) {
            throw new PasswordResetRequestIsNotActiveAnyMore();
        }

        if (ZonedDateTime.now().isAfter(passwordResetRequest.getExpires())) {
            throw new PasswordResetRequestExpired();
        }

        return passwordResetRequest;
    }

}
