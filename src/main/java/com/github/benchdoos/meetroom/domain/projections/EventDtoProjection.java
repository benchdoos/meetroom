package com.github.benchdoos.meetroom.domain.projections;

import com.github.benchdoos.meetroom.domain.dto.MeetingRoomDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface EventDtoProjection {
    UUID getId();

    UserPublicInfoDto getCreator();

    MeetingRoomDto getMeetingRoom();

    ZonedDateTime getFromDate();

    ZonedDateTime getToDate();

    String getTitle();

    String getDescription();

    Boolean getDeleted();
}
