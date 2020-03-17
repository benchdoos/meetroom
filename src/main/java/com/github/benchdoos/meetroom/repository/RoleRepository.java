package com.github.benchdoos.meetroom.repository;

import com.github.benchdoos.meetroom.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    /**
     * Get role by name
     *
     * @param name role name
     * @return role by given name
     */
    Role findFirstByRole(String name);

    /**
     * Get first role that has given role and name
     *
     * @param role role
     * @param name name
     * @return optional of role
     */
    Optional<Role> findFirstByRoleOrName(String role, String name);

}
