package com.github.benchdoos.meetroom.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "account_activation_requests")
public class AccountActivationRequest {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private ZonedDateTime requested;

    @NotNull
    private ZonedDateTime expires;

    /**
     * Account activation was requested for this user
     */
    @NotNull
    @ManyToOne(cascade = CascadeType.DETACH)
    private User requestedFor;

    @NotNull
    private boolean active;
}
