package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.UUID;

/**
 * DTO with extended {@link User} information
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserExtendedInfoDto extends UserPublicInfoDto {

    /**
     * Builder for {@link UserExtendedInfoDto} instead of parent ({@link UserPublicInfoDto}) builder
     *
     * @param id id
     * @param username username
     * @param firstName first name
     * @param lastName last name
     * @param enabled enabled
     * @param roles roles of user
     */
    @Builder(builderMethodName = "realBuilder")
    public UserExtendedInfoDto(UUID id, String username, String firstName, String lastName, Boolean enabled, Collection<UserRole> roles) {
        super(id, username, firstName, lastName, enabled);
        this.roles = roles;
    }

    private Collection<UserRole> roles;
}
