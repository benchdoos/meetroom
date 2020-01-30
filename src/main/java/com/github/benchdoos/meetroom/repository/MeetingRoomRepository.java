package com.github.benchdoos.meetroom.repository;

import com.github.benchdoos.meetroom.domain.MeetingRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * JPA repository to operate with {@link MeetingRoom}
 */
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, UUID> {
    Page<MeetingRoom> findAllByDisabled(Boolean disabled, Pageable pageable);
}
