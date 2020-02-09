package com.github.benchdoos.meetroom.exceptions;

import com.github.benchdoos.meetroom.domain.MeetingRoom;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MeetingRoomAlreadyExistException extends RuntimeException {
    public MeetingRoomAlreadyExistException(MeetingRoom meetingRoom) {
        super(String.format("Meeting room already exists (name: %s location: %s)",
                meetingRoom.getName(), meetingRoom.getLocation()));
    }
}
