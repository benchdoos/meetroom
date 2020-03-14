package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.Privilege;

import java.util.List;

public interface PrivilegeService {

    /**
     * Get all privileges, sorted by name ASC
     *
     * @return list of privileges
     */
    List<Privilege> getAllPrivileges();
}
