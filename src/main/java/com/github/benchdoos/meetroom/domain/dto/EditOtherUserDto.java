package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EditOtherUserDto implements UserInfo {

    private String username;

    private String firstName;

    private String lastName;
}
