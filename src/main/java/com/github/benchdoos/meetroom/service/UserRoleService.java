package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.UserRole;
import com.github.benchdoos.meetroom.domain.dto.EditUserRoleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

/**
 * Service to operate with {@link UserRole}
 */
public interface UserRoleService {

    /**
     * Find all roles, sorted
     *
     * @param sort sort by
     * @return list of sorted roles
     */
    List<UserRole> getAllUserRoles(Sort sort);

    /**
     * Find all user role by pageable
     *
     * @param pageable pageable
     * @return pageable for all roles
     */
    Page<UserRole> findAllUserRoles(Pageable pageable);

    /**
     * Update {@link UserRole} by id
     *
     * @param id role id
     * @param editUserRoleDto dto with updates
     * @return updated role
     */
    UserRole updateUserRole(UUID id, EditUserRoleDto editUserRoleDto);
}
