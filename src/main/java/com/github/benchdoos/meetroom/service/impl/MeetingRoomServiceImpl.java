package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.MeetingRoomDto;
import com.github.benchdoos.meetroom.exceptions.MeetingRoomNotFoundException;
import com.github.benchdoos.meetroom.mappers.MeetingRoomMapper;
import com.github.benchdoos.meetroom.repository.MeetingRoomRepository;
import com.github.benchdoos.meetroom.service.MeetingRoomService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {
    private final MeetingRoomRepository meetingRoomRepository;

    @Override
    public MeetingRoom getById(UUID id) {
        return meetingRoomRepository.findById(id).orElseThrow(MeetingRoomNotFoundException::new);
    }

    @Override
    public MeetingRoom createMeetingRoom(MeetingRoomDto meetingRoomDto) {
        final MeetingRoom meetingRoom = new MeetingRoom();

        final MeetingRoomMapper meetingRoomMapper = Mappers.getMapper(MeetingRoomMapper.class);
        meetingRoomMapper.convertDtoToEntity(meetingRoomDto, meetingRoom);

        return meetingRoomRepository.save(meetingRoom);
    }

    @Override
    public Page<MeetingRoom> findAll(Pageable pageable) {
        return meetingRoomRepository.findAll(pageable);
    }

    @Override
    public Page<MeetingRoom> getAllAvailable(Pageable pageable) {
        return meetingRoomRepository.findAllByDisabled(Boolean.FALSE, pageable);
    }
}
