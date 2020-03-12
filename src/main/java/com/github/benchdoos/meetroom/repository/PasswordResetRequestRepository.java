package com.github.benchdoos.meetroom.repository;

import com.github.benchdoos.meetroom.domain.PasswordResetRequest;
import com.github.benchdoos.meetroom.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.UUID;

public interface PasswordResetRequestRepository extends JpaRepository<PasswordResetRequest, UUID> {

    Collection<PasswordResetRequest> findByRequestedForAndExpiresIsAfterAndActiveIsTrue(User user, ZonedDateTime now);

}
