package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.MeetingRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Dto for {@link MeetingRoom}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateMeetingRoomDto {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    private Boolean enabled;
}
