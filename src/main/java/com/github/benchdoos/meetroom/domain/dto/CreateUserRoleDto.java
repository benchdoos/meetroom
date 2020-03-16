package com.github.benchdoos.meetroom.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.benchdoos.meetroom.domain.annotations.PrivilegeOrRoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateUserRoleDto {

    @NotBlank
    private String name;

    @NotBlank
    @PrivilegeOrRoleName
    private String role;

    @JsonDeserialize(contentAs = CreateUserRoleDto.class)
    private List<UUID> privileges = Collections.emptyList();

    public void setPrivileges(List<UUID> privileges) {
        if (privileges != null) {
            this.privileges = privileges;
        }
    }
}
