package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.MeetingRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * DTOwith information about {@link MeetingRoom}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MeetingRoomDto {

    @NotNull
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    private boolean enabled;
}
