package com.github.benchdoos.meetroom.controller;

import com.github.benchdoos.meetroom.domain.MeetingRoom;
import com.github.benchdoos.meetroom.domain.dto.CreateMeetingRoomDto;
import com.github.benchdoos.meetroom.domain.dto.EditMeetingRoomDto;
import com.github.benchdoos.meetroom.service.MeetingRoomService;
import com.github.benchdoos.meetroom.service.ModelViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/meetroom")
public class MeetroomController {
    private final MeetingRoomService meetingRoomService;
    private final ModelViewService modelViewService;

    /**
     * Get meeting room page by meeting room id
     *
     * @param id id
     * @param day show room week for given date
     * @param model model
     * @return page
     */
    @PreAuthorize("hasAnyAuthority('MEETING_ROOM:USE')")
    @GetMapping("/{id}")
    public String getMeetingRoomById(@PathVariable UUID id,

                                     @RequestParam(value = "day", required = false)
                                     @DateTimeFormat(pattern = "dd.MM.yyyy") Date day,

                                     Model model) {
        if (day != null) {
            final ZonedDateTime fromDate = ZonedDateTime.ofInstant(day.toInstant(), ZoneId.systemDefault());
            return modelViewService.getMeetingRoomById(id, fromDate, model);
        }

        return modelViewService.getMeetingRoomById(id, null, model);
    }

    @PreAuthorize("hasAnyAuthority('MEETING_ROOM:CREATE')")
    @PostMapping
    public String createMeetRoom(@Validated CreateMeetingRoomDto meetingRoomDto) {
        final MeetingRoom meetingRoom = meetingRoomService.createMeetingRoom(meetingRoomDto);
        return "redirect:/meetroom/" + meetingRoom.getId();
    }

    @PreAuthorize("hasAnyAuthority('MEETING_ROOM:UPDATE')")
    @PostMapping("/edit/{uuid}")
    public String editMeetRoom(@PathVariable UUID uuid,
                               @Validated EditMeetingRoomDto editMeetingRoomDto) {
        final MeetingRoom meetingRoom = meetingRoomService.updateMeetingRoomInfo(uuid, editMeetingRoomDto);
        final UUID id = meetingRoom.getId();
        return "redirect:/meetroom/" + meetingRoom.getId();
    }
}
