package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.Role;
import com.github.benchdoos.meetroom.domain.User;
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

    private Collection<Role> roles;

    /**
     * Builder for {@link UserExtendedInfoDto} instead of parent ({@link UserPublicInfoDto}) builder
     *
     * @param id id
     * @param username username
     * @param firstName first name
     * @param lastName last name
     * @param email user email
     * @param enabled enabled
     * @param roles roles of user
     */
    @Builder(builderMethodName = "realBuilder")
    public UserExtendedInfoDto(UUID id,
                               String username,
                               String firstName,
                               String lastName,
                               String email,
                               Boolean enabled,
                               Collection<Role> roles,
                               UserAvatarDto avatar) {
        super(id, username, firstName, lastName, email, enabled, avatar);
        this.roles = roles;
    }
}
