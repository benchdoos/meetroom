package com.github.benchdoos.meetroom.repository;

import com.github.benchdoos.meetroom.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

    Optional<UserRole> findFirstByRoleOrName(String role, String name);
}
