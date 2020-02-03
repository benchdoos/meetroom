package com.github.benchdoos.meetroom.repository;

import com.github.benchdoos.meetroom.domain.MeetingRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * JPA repository to operate with {@link MeetingRoom}
 */
public interface MeetingRoomsRepository extends JpaRepository<MeetingRoom, UUID> {
    Page<MeetingRoom> findAllByEnabled(boolean enabled, Pageable pageable);
}
