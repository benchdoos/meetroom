package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.UserEmailUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Public data of {@link UserEmailUpdateRequest}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserEmailUpdateRequestDto {

    private UUID id;

    private ZonedDateTime requested;

    private ZonedDateTime expires;

    private boolean oldEmailAddressActivated;

    private boolean newEmailAddressActivated;

}
