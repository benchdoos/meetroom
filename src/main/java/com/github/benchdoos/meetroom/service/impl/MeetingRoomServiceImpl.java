package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.CreateMeetingRoomDto;
import com.github.benchdoos.meetroom.domain.dto.EditMeetingRoomDto;
import com.github.benchdoos.meetroom.exceptions.MeetingRoomAlreadyExistException;
import com.github.benchdoos.meetroom.exceptions.MeetingRoomNotFoundException;
import com.github.benchdoos.meetroom.mappers.MeetingRoomMapper;
import com.github.benchdoos.meetroom.repository.MeetingRoomsRepository;
import com.github.benchdoos.meetroom.service.MeetingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
    public MeetingRoom createMeetingRoom(CreateMeetingRoomDto meetingRoomDto) {
        final Optional<MeetingRoom> firstByNameOrLocation = meetingRoomsRepository
                .findFirstByNameOrLocation(meetingRoomDto.getName(), meetingRoomDto.getLocation());
        if (firstByNameOrLocation.isPresent()) {
            throw new MeetingRoomAlreadyExistException(firstByNameOrLocation.get());
        }

        final MeetingRoom meetingRoom = new MeetingRoom();

        meetingRoomMapper.convert(meetingRoomDto, meetingRoom);

        meetingRoom.setEnabled(true);

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

    @Override
    public MeetingRoom updateMeetingRoomInfo(UUID id, EditMeetingRoomDto editMeetingRoomDto) {
        final Optional<MeetingRoom> firstByNameOrLocation = meetingRoomsRepository
                .findFirstByNameOrLocation(editMeetingRoomDto.getName(), editMeetingRoomDto.getLocation());

        if (firstByNameOrLocation.isPresent() && !firstByNameOrLocation.get().getId().equals(id)) {
            throw new MeetingRoomAlreadyExistException(firstByNameOrLocation.get());
        }

        final MeetingRoom meetingRoom = meetingRoomsRepository.findById(id).orElseThrow(MeetingRoomNotFoundException::new);

        meetingRoom.setName(editMeetingRoomDto.getName());
        meetingRoom.setLocation(editMeetingRoomDto.getLocation());

        if (editMeetingRoomDto.getEnabled() != null) {
            meetingRoom.setEnabled(editMeetingRoomDto.getEnabled());
        }

        return meetingRoomsRepository.save(meetingRoom);
    }
}
