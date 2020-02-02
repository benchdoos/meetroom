package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.config.constants.AuthoritiesConstants;
import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.MeetingRoomDto;
import com.github.benchdoos.meetroom.service.MeetingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/meeting-room")
public class MeetingRoomController {
    private final MeetingRoomService meetingRoomService;

    @GetMapping("/list-rooms")
    public Page<MeetingRoom> listRooms(@PageableDefault Pageable pageable) {
        return meetingRoomService.findAll(pageable);
    }

    @Secured(AuthoritiesConstants.ROLE_ADMIN)
    @PostMapping
    public MeetingRoom createMeetingRoom(@Validated MeetingRoomDto meetingRoomDto) {
        return meetingRoomService.createMeetingRoom(meetingRoomDto);
    }
}
