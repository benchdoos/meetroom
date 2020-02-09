package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * DTOwith information about {@link Event}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EventDto {

    @NotNull
    private UUID id;

    @NotNull
    private UserPublicInfoDto creator;

    @NotNull
    private MeetingRoomDto meetingRoom;

    @NotNull
    private ZonedDateTime fromDate;

    @NotNull
    private ZonedDateTime toDate;

    @Nullable
    private String title;

    @Nullable
    private String description;

    @Nullable
    private Boolean deleted;
}
