package com.github.benchdoos.meetroom.domain;

import com.github.benchdoos.meetroom.domain.annotations.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_email_update_requests")
public class UserEmailUpdateRequest {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private ZonedDateTime requested;

    @NotNull
    private ZonedDateTime expires;

    /**
     * Email change was requested for this user
     */
    @NotNull
    @ManyToOne(cascade = CascadeType.DETACH)
    private User requestedFor;

    @Column(unique = true)
    private UUID oldEmailConfirmation;

    @Column(unique = true)
    private UUID newEmailConfirmation;

    @Email
    @Size(min = 4, max = 320)
    private String newEmailAddress;

    private boolean oldEmailAddressActivated;

    private boolean newEmailAddressActivated;

    @NotNull
    private boolean active;
}
