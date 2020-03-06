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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Default implementation for {@link UserService}
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

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
    public UserPublicInfoDto createUser(CreateUserDto createUserDto) {
        validateNewUser(createUserDto);

        final UserRole userRole = rolesRepository.findFirstByRole(SecurityConstants.ROLE_USER);

        final User user = User.builder()
                .firstName(createUserDto.getFirstName())
                .lastName(createUserDto.getLastName())
                .username(createUserDto.getUsername())
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .roles(Collections.singleton(userRole))
                .enabled(true)
                .build();

        final User savedUser = userRepository.save(user);
        log.info("Successfully created user with username: {}", savedUser.getUsername());
        final UserPublicInfoDto userPublicInfoDto = new UserPublicInfoDto();
        userMapper.convert(savedUser, userPublicInfoDto);

        return userPublicInfoDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can not find user by username: " + username));

        checkUserIsNotDisabled(user);

        final UserDetailsDto userDetailsDto = new UserDetailsDto(user);

        userDetailsDto.setAuthorities(getGrantedAuthoritiesFromUserRoles(user.getRoles()));

        return userDetailsDto;
    }

    /**
     * Validate user for creation
     *
     * @param createUserDto dto with user
     */
    private void validateNewUser(CreateUserDto createUserDto) {

        final Optional<User> byUsername = userRepository.findByUsername(createUserDto.getUsername());

        if (byUsername.isPresent()) {
            throw new UserAlreadyExistsException(createUserDto.getUsername());
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

    /**
     * Transforms list of {@link UserRole} to {@link GrantedAuthority} final List<>  = new ();
     *
     * @param roles list of user's roles
     * @return list of granted authorities
     */
    private List<GrantedAuthority> getGrantedAuthoritiesFromUserRoles(Collection<UserRole> roles) {
        final List<GrantedAuthority> grantList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            roles.forEach(role -> {
                grantList.add(new SimpleGrantedAuthority(role.getRole()));
                role.getPrivileges().forEach(privilege -> grantList.add(new SimpleGrantedAuthority(privilege.getName())));
            });
        }
        return grantList;
    }
}
