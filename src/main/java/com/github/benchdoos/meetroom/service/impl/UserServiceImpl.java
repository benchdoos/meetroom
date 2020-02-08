package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.UserRole;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UserDetailsDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.exceptions.UserDisabledException;
import com.github.benchdoos.meetroom.exceptions.UserNotFoundException;
import com.github.benchdoos.meetroom.mappers.UserMapper;
import com.github.benchdoos.meetroom.repository.UserRepository;
import com.github.benchdoos.meetroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Default implementation for {@link UserService}
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserPublicInfoDto getUserPublicInfoDtoByUsername(String username) {
        final User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        final UserPublicInfoDto userPublicInfoDto = new UserPublicInfoDto();

        userMapper.convert(user, userPublicInfoDto);

        return userPublicInfoDto;
    }

    public UserExtendedInfoDto getExtendedUserInfoDtoByUsername(String username) {
        final User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        final UserExtendedInfoDto userExtendedInfoDto = new UserExtendedInfoDto();

        userMapper.convert(user, userExtendedInfoDto);

        return userExtendedInfoDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        checkUserIsNotDisabled(user);

        @NotEmpty final Collection<UserRole> roles = user.getRoles();

        return new UserDetailsDto(user);
    }

    /**
     * Validate user on availability
     *
     * @param user to check
     */
    private void checkUserIsNotDisabled(@NotNull User user) {
        if (!user.isEnabled()) {
            throw new UserDisabledException(user.getUsername());
        }
    }
}
