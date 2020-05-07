package com.github.benchdoos.meetroom.repository;

import com.github.benchdoos.meetroom.domain.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AvatarRepository extends JpaRepository<Avatar, UUID> {
}
