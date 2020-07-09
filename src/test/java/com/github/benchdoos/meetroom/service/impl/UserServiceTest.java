package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.abstracts.AbstractIntegrationCommonTest;
import com.github.benchdoos.meetroom.config.TestMailSenderConfig;
import com.github.benchdoos.meetroom.config.beans.SpringConfigurationInfoBean;
import com.github.benchdoos.meetroom.domain.dto.CreateUserDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.repository.UserRepository;
import com.github.benchdoos.meetroom.service.EmailService;
import com.github.benchdoos.meetroom.service.UserService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest extends AbstractIntegrationCommonTest {
    @Autowired
    private UserRepository userRepository;

    @MockBean
    private SpringConfigurationInfoBean springConfigurationInfoBean;

    @MockBean
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);

        Mockito.when(springConfigurationInfoBean.getPublicFullApplicationUrl()).thenReturn("http://valid-service-url.com/");
    }


    @Test
    public void createNewUserMustCreateNewUser() {
        final CreateUserDto createUserDto = CreateUserDto.builder()
                .firstName("First name")
                .lastName("Last name")
                .password("qwertyuiop")
                .confirmPassword("qwertyuiop")
                .username("correctusername")
                .email("correct-email@mail.com")
                .build();
        final UserPublicInfoDto user = userService.createUser(createUserDto);

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(createUserDto.getUsername());
        assertThat(user.getFirstName()).isEqualTo(createUserDto.getFirstName());
        assertThat(user.getLastName()).isEqualTo(createUserDto.getLastName());
        assertThat(user.getEmail()).isEqualTo(createUserDto.getEmail());
        assertThat(user.getEnabled()).isEqualTo(true);
        assertThat(user.getAvatar()).isNotNull();
    }

}