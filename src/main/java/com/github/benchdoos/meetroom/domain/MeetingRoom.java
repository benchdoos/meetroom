package com.github.benchdoos.meetroom.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "meeting_rooms")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MeetingRoom {
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Name of meeting room
     */
    @NotBlank
    @Column(unique = true)
    private String name;

    /**
     * Room location
     */
    @NotBlank
    @Column(unique = true)
    private String location;

    /**
     * Is room disabled
     */
    private boolean enabled;
}
