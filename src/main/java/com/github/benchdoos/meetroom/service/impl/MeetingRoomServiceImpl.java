package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.MeetingRoomDto;
import com.github.benchdoos.meetroom.exceptions.MeetingRoomNotFoundException;
import com.github.benchdoos.meetroom.mappers.MeetingRoomMapper;
import com.github.benchdoos.meetroom.repository.MeetingRoomsRepository;
import com.github.benchdoos.meetroom.service.MeetingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {
    private final MeetingRoomsRepository meetingRoomsRepository;
    private final MeetingRoomMapper meetingRoomMapper;

    @Override
    public MeetingRoom getById(UUID id) {
        return meetingRoomsRepository.findById(id).orElseThrow(MeetingRoomNotFoundException::new);
    }

    @Override
    public MeetingRoom createMeetingRoom(MeetingRoomDto meetingRoomDto) {
        final MeetingRoom meetingRoom = new MeetingRoom();

        meetingRoomMapper.convert(meetingRoomDto, meetingRoom);

        return meetingRoomsRepository.save(meetingRoom);
    }

    @Override
    public Page<MeetingRoom> findAll(Pageable pageable) {
        return meetingRoomsRepository.findAll(pageable);
    }

    @Override
    public Page<MeetingRoom> getAllAvailable(Pageable pageable) {
        return meetingRoomsRepository.findAllByEnabled(Boolean.TRUE, pageable);
    }
}
