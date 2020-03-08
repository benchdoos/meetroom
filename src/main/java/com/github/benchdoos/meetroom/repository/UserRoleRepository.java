package com.github.benchdoos.meetroom.repository;

import com.github.benchdoos.meetroom.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

}
