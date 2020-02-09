package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.MeetingRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * DTOfor {@link MeetingRoom}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EditMeetingRoomDto {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    private Boolean enabled;
}
