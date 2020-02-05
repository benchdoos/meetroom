package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.MeetingRoomDto;
import com.github.benchdoos.meetroom.service.IndexViewService;
import com.github.benchdoos.meetroom.service.MeetingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/meetroom")
public class MeetroomController {
    private final MeetingRoomService meetingRoomService;
    private final IndexViewService indexViewService;


    @Secured("ROLE_ADMIN")
    @PostMapping
    public String createMeetRoom(@Validated MeetingRoomDto meetingRoomDto, Model model) {
        final MeetingRoom meetingRoom = meetingRoomService.createMeetingRoom(meetingRoomDto);
        final UUID id = meetingRoom.getId();
        return indexViewService.getMeetingRoomById(id, null, model);
    }
}
