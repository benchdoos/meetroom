package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.config.constants.SecurityConstants;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.UserRole;
import com.github.benchdoos.meetroom.domain.dto.CreateUserDto;
import com.github.benchdoos.meetroom.domain.dto.UserDetailsDto;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.exceptions.UserAlreadyExistsException;
import com.github.benchdoos.meetroom.exceptions.UserDisabledException;
import com.github.benchdoos.meetroom.exceptions.UserNotFoundException;
import com.github.benchdoos.meetroom.mappers.UserMapper;
import com.github.benchdoos.meetroom.repository.RolesRepository;
import com.github.benchdoos.meetroom.repository.UserRepository;
import com.github.benchdoos.meetroom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

/**
 * Default implementation for {@link UserService}
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RolesRepository rolesRepository;

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
    public User getById(UUID id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserPublicInfoDto createUser(CreateUserDto createUserDto, BindingResult bindingResult) {
        validateNewUser(createUserDto, bindingResult);

        if (bindingResult.hasErrors()) {
//            throw new DataBindingException();
        }
        final UserRole userRole = rolesRepository.findFirstByName(SecurityConstants.ROLE_USER);

        final User user = User.builder()
                .firstName(createUserDto.getFirstName())
                .lastName(createUserDto.getLastName())
                .username(createUserDto.getUsername())
                .password(createUserDto.getPassword()) //todo md5?
                .roles(Collections.singletonList(userRole))
                .enabled(true)
                .build();

        final User savedUser = userRepository.save(user);

        final UserPublicInfoDto userPublicInfoDto = new UserPublicInfoDto();
        userMapper.convert(savedUser, userPublicInfoDto);

        return userPublicInfoDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        checkUserIsNotDisabled(user);

        @NotEmpty final Collection<UserRole> roles = user.getRoles();

        return new UserDetailsDto(user);
    }

    /**
     * Validate user for creation
     *
     * @param createUserDto dto with user
     * @param bindingResult bindingResult for rejection
     */
    private void validateNewUser(CreateUserDto createUserDto, BindingResult bindingResult) {
        final Optional<User> byUsername = userRepository.findByUsername(createUserDto.getUsername());
        if (byUsername.isPresent()) {
            throw new UserAlreadyExistsException(createUserDto.getUsername());
        }

        if (!createUserDto.getPassword().equals(createUserDto.getConfirmPassword())) {
            bindingResult.rejectValue("password", "passwords not match", "Passwords do not match");
        }

        if (bindingResult.hasErrors()) {
            throw new WebExchangeBindException(null ,bindingResult); //todo fix null
        }
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
