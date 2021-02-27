package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.abstracts.AbstractIntegrationCommonTest;
import com.github.benchdoos.meetroom.config.beans.SpringConfigurationInfoBean;
import com.github.benchdoos.meetroom.domain.dto.CreateUserDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.exceptions.EmailAlreadyExistsException;
import com.github.benchdoos.meetroom.exceptions.UserAlreadyExistsException;
import com.github.benchdoos.meetroom.repository.UserRepository;
import com.github.benchdoos.meetroom.service.EmailService;
import com.github.benchdoos.meetroom.service.UserService;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest extends AbstractIntegrationCommonTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @MockBean
    private SpringConfigurationInfoBean springConfigurationInfoBean;

    @MockBean
    private EmailService emailService;

    @Override
    public void setUp() throws Exception {

        super.setUp();
        MockitoAnnotations.initMocks(this);

        Mockito.when(springConfigurationInfoBean.getPublicFullApplicationUrl())
                .thenReturn("http://valid-service-url.com/");
    }

    @Test
    public void createNewUserMustCreateNewUser() {

        final CreateUserDto createUserDto =
                CreateUserDto.builder()
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

    @Test(expected = UserAlreadyExistsException.class)
    public void createNewUserMustThrowUserAlreadyExistsException() {

        final CreateUserDto alreadyCreatedUser = CreateUserDto.builder()
                .firstName("First name")
                .lastName("Last name")
                .password("qwertyuiop")
                .confirmPassword("qwertyuiop")
                .username("user") //important
                .email("correct-email@mail.com")
                .build();
        final UserPublicInfoDto user = userService.createUser(alreadyCreatedUser);
    }

    @Sql("/sql/UserServiceTest.sql")
    @Test(expected = EmailAlreadyExistsException.class)
    public void createNewUserMustThrowEmailAlreadyExistsException() {

        final CreateUserDto alreadyCreatedUser = CreateUserDto.builder()
                .firstName("First name")
                .lastName("Last name")
                .password("qwertyuiop")
                .confirmPassword("qwertyuiop")
                .username("correctusername")
                .email("correct-test-user-email@mail.com") //important
                .build();

        final UserPublicInfoDto user = userService.createUser(alreadyCreatedUser);
    }

}
