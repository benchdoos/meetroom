package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.Role;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.dto.CreateRoleDto;
import com.github.benchdoos.meetroom.domain.dto.EditRoleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Service to operate with {@link Role}
 */
public interface RoleService {

    /**
     * Find all roles, sorted
     *
     * @param sort sort by
     * @return list of sorted roles
     */
    List<Role> getAllRoles(Sort sort);

    /**
     * Find all user role by pageable
     *
     * @param pageable pageable
     * @return pageable for all roles
     */
    Page<Role> findAllRoles(Pageable pageable);

    /**
     * Get role by id
     *
     * @param id role id
     * @return role if found
     */
    Role getRole(UUID id);

    /**
     * Update {@link Role} by id
     *
     * @param id role id
     * @param editRoleDto dto with updates
     * @return updated role
     */
    Role updateRole(UUID id, EditRoleDto editRoleDto);

    /**
     * Create {@link Role}
     *
     * @param createRoleDto dto to create
     * @return created role
     */
    Role createRole(CreateRoleDto createRoleDto);

    /**
     * Delete role by id
     *
     * @param id role id
     */
    void deleteRole(UUID id);

    /**
     * Returns all active users for role
     *
     * @param role role
     * @return list of users
     */
    Collection<User> getTotalActiveUsers(Role role);

    /**
     * Search roles by name and internal name
     *
     * @param request request
     * @param pageable pageable
     * @return result page
     */
    Page<Role> searchRolesByNames(String request, Pageable pageable);
}
