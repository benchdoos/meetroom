package com.github.benchdoos.meetroom.service;

import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.MeetingRoomDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service that gives ability to operate with {@link MeetingRoom}
 */
public interface MeetingRoomService {

    /**
     * Get meeting room by id
     *
     * @param id of room
     * @return meeting room
     */
    MeetingRoom getById(UUID id);

    /**
     * Create meeting room
     *
     * @param meetingRoomDto dto of {@link MeetingRoom} to create
     * @return created meeting room
     */
    MeetingRoom createMeetingRoom(MeetingRoomDto meetingRoomDto);

    /**
     * Find all available meeting rooms
     *
     * @param pageable pageable
     * @return all available rooms
     */
    Page<MeetingRoom> findAll(Pageable pageable);

    /**
     * Find all not disabled meeting rooms.
     *
     * @param pageable rooms
     * @return all avaible rooms for use
     */
    Page<MeetingRoom> getAllAvailable(Pageable pageable);
}
