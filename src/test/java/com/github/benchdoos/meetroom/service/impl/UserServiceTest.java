package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.abstracts.AbstractIntegrationCommonTest;
import com.github.benchdoos.meetroom.domain.dto.CreateUserDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.repository.UserRepository;
import com.github.benchdoos.meetroom.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest extends AbstractIntegrationCommonTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }


    @Test
    public void createNewUserMustCreateNewUser() {
        final CreateUserDto createUserDto = CreateUserDto.builder()
                .firstName("First name")
                .lastName("Last name")
                .password("qwertyuiop")
                .confirmPassword("qwertyuiop")
                .username("correctusername")
                .build();
        final UserPublicInfoDto user = userService.createUser(createUserDto);

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(createUserDto.getUsername());
        assertThat(user.getFirstName()).isEqualTo(createUserDto.getFirstName());
        assertThat(user.getLastName()).isEqualTo(createUserDto.getLastName());
        assertThat(user.getEnabled()).isEqualTo(true);
        assertThat(user.getAvatar()).isNotNull();
    }

}