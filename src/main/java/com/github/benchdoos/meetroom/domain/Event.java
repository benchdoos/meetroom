package com.github.benchdoos.meetroom.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Event {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private MeetingRoom meetingRoom;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private User user;

    @NotNull
    @Column(name = "from_date")
    private ZonedDateTime fromDate;

    @NotNull
    @Column(name = "to_date")
    private ZonedDateTime toDate;

    @Nullable
    @Column(name = "title")
    private String title;

    @Nullable
    @Column(name = "description", length = 3000)
    private String description;

    @Nullable
    private Boolean deleted;
}
