package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Dto with extended {@link User} information
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserExtendedInfoDto extends UserPublicInfoDto {

    private Set<UserRole> roles;
}
