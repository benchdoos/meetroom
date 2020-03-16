package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

/**
 * Dto to update roles for user
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EditRolesForUserDto {

    /**
     * {@link UserRole} ids
     */
    @NotEmpty
    private List<UUID> roles;
}
