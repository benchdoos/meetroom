package com.github.benchdoos.meetroom.repository;

import com.github.benchdoos.meetroom.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RolesRepository extends JpaRepository<UserRole, UUID> {

    /**
     * Get role by name
     *
     * @param name role name
     * @return UserRole by given name
     */
    UserRole findFirstByName(String name);
}
