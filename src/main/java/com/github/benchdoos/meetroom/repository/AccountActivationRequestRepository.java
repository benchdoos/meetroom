package com.github.benchdoos.meetroom.repository;

import com.github.benchdoos.meetroom.domain.AccountActivationRequest;
import com.github.benchdoos.meetroom.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.UUID;

public interface AccountActivationRequestRepository extends JpaRepository<AccountActivationRequest, UUID> {

    Collection<AccountActivationRequest> findByRequestedForAndExpiresIsAfterAndActiveIsTrue(User user, ZonedDateTime date);
}
