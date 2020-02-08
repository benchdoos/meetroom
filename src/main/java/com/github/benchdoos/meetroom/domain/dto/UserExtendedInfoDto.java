package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserExtendedInfoDto extends UserPublicInfoDto {

    private Set<UserRole> roles;
}
