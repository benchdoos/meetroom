package com.github.benchdoos.meetroom.repository;

import com.github.benchdoos.meetroom.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository to operate with {@link Privilege}
 */
public interface PrivilegeRepository extends JpaRepository<Privilege, UUID> {

}
