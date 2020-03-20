package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.Privilege;

import java.util.List;
import java.util.UUID;

public interface PrivilegeService {

    /**
     * Get all privileges, sorted by name ASC
     *
     * @return list of privileges
     */
    List<Privilege> getAllPrivileges();

    /**
     * Get privileges by ids
     *
     * @param ids ids
     * @return privileges
     */
    List<Privilege> findAllByIds(Iterable<UUID> ids);
}
