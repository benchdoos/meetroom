package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Service to operate with {@link UserRole}
 */
public interface UserRoleService {

    List<UserRole> getAllUserRoles(Sort name);

    Page<UserRole> findAllUserRoles(Pageable pageable);
}
