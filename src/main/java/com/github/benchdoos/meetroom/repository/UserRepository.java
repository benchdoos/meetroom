package com.github.benchdoos.meetroom.repository;

import com.github.benchdoos.meetroom.domain.Role;
import com.github.benchdoos.meetroom.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    Optional<User> findFirstByUsername(String username);

    Optional<User> findFirstByEmail(String email);

    Page<User> findAll(@Nullable Specification<User> specification, @Nullable Pageable pageable);

    Collection<User> findAllByRolesIn(List<Role> roles);

    Collection<User> findAllByRolesInAndEnabledIsTrue(List<Role> roles);
}
