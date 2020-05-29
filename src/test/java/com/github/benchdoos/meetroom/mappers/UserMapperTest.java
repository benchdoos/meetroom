package com.github.benchdoos.meetroom.mappers;

import com.github.benchdoos.meetroom.abstracts.AbstractIntegrationCommonTest;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest extends AbstractIntegrationCommonTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void convertUserToUserPublicInfoDto() {
        final User testUser = easyRandom.nextObject(User.class);
        final UserPublicInfoDto correctResult = getCorrectUserPublicInfoDto(testUser);

        final UserPublicInfoDto resultDto = new UserPublicInfoDto();
        userMapper.convert(testUser, resultDto);

        assertThat(resultDto).isNotNull();
        assertThat(resultDto).isEqualTo(correctResult);
    }

    @Test
    public void testConvertUserPublicInfoDtoToUser() {
        final UserPublicInfoDto testDto = easyRandom.nextObject(UserPublicInfoDto.class);
        final User correctResult = getCorrectUser(testDto);

        final User resultUser = new User();
        userMapper.convert(testDto, resultUser);

        assertThat(resultUser).isNotNull();
        assertThat(resultUser).isEqualTo(correctResult);
    }

    @Test
    public void testConvertUserToUserExtendedInfoDto() {
        final User testUser = easyRandom.nextObject(User.class);
        final UserExtendedInfoDto correctResult = getCorrectUserExtendedInfoDto(testUser);

        final UserExtendedInfoDto resultDto = new UserExtendedInfoDto();
        userMapper.convert(testUser, resultDto);

        assertThat(resultDto).isNotNull();
        assertThat(resultDto).isEqualTo(correctResult);
    }

    @Test
    public void testConvertUserExtendedInfoDtoToUser() {
        final User correctUser = easyRandom.nextObject(User.class);
        correctUser.setPassword(null);
        final UserExtendedInfoDto testDto = getCorrectUserExtendedInfoDto(correctUser);

        final User resultUser = new User();
        userMapper.convert(testDto, resultUser);

        assertThat(resultUser).isNotNull();
        assertThat(resultUser).isEqualTo(correctUser);
    }

    private User getCorrectUser(UserPublicInfoDto testDto) {
        return User.builder()
                .id(testDto.getId())
                .firstName(testDto.getFirstName())
                .lastName(testDto.getLastName())
                .username(testDto.getUsername())
                .roles(null) // important
                .enabled(testDto.getEnabled())
                .build();
    }

    private UserPublicInfoDto getCorrectUserPublicInfoDto(User testUser) {
        return UserPublicInfoDto.builder()
                .id(testUser.getId())
                .firstName(testUser.getFirstName())
                .lastName(testUser.getLastName())
                .username(testUser.getUsername())
                .enabled(testUser.isEnabled())
                .build();
    }

    private UserExtendedInfoDto getCorrectUserExtendedInfoDto(User testUser) {
        return UserExtendedInfoDto.realBuilder()
                .id(testUser.getId())
                .username(testUser.getUsername())
                .firstName(testUser.getFirstName())
                .lastName(testUser.getLastName())
                .enabled(testUser.isEnabled())
                .roles(testUser.getRoles())
                .build();
    }
}