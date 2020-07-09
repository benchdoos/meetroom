package com.github.benchdoos.meetroom.repository;

import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.UserEmailUpdateRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface UserEmailUpdateRequestRepository extends
        JpaRepository<UserEmailUpdateRequest, UUID>,
        JpaSpecificationExecutor<UserEmailUpdateRequest> {

    Optional<UserEmailUpdateRequest> findOne(Specification<UserEmailUpdateRequest> specification);

    Collection<UserEmailUpdateRequest> findAllByRequestedFor(User user);
}
