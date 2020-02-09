package com.github.benchdoos.meetroom.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

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
    private MeetingRoomInfoDto meetingRoom;

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
