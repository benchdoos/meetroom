package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.CreateMeetingRoomDto;
import com.github.benchdoos.meetroom.domain.dto.EditMeetingRoomDto;
import com.github.benchdoos.meetroom.service.MeetingRoomService;
import com.github.benchdoos.meetroom.service.ModelViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/meetroom")
public class MeetroomController {
    private final MeetingRoomService meetingRoomService;
    private final ModelViewService modelViewService;

    @PreAuthorize("hasAnyAuthority('MEETING_ROOM:CREATE')")
    @PostMapping
    public String createMeetRoom(@Validated CreateMeetingRoomDto meetingRoomDto, Model model) {
        final MeetingRoom meetingRoom = meetingRoomService.createMeetingRoom(meetingRoomDto);
        final UUID id = meetingRoom.getId();
        return modelViewService.getMeetingRoomById(id, null, model);
    }

    @PreAuthorize("hasAnyAuthority('MEETING_ROOM:UPDATE')")
    @PostMapping("/edit/{uuid}")
    public String editMeetRoom(@PathVariable UUID uuid,
                               @Validated EditMeetingRoomDto editMeetingRoomDto,
                               Model model) {
        final MeetingRoom meetingRoom = meetingRoomService.updateMeetingRoomInfo(uuid, editMeetingRoomDto);
        final UUID id = meetingRoom.getId();
        return modelViewService.getMeetingRoomById(id, null, model);
    }
}
