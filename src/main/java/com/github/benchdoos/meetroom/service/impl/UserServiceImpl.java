package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.config.constants.SecurityConstants;
import com.github.benchdoos.meetroom.config.properties.InternalConfiguration;
import com.github.benchdoos.meetroom.domain.Avatar;
import com.github.benchdoos.meetroom.domain.PasswordResetRequest;
import com.github.benchdoos.meetroom.domain.Role;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.dto.CreateOtherUserDto;
import com.github.benchdoos.meetroom.domain.dto.CreateUserDto;
import com.github.benchdoos.meetroom.domain.dto.EditOtherUserDto;
import com.github.benchdoos.meetroom.domain.dto.EditRolesForUserDto;
import com.github.benchdoos.meetroom.domain.dto.EditUserUsernameDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserAvatarDto;
import com.github.benchdoos.meetroom.domain.dto.UserAvatarDto;
import com.github.benchdoos.meetroom.domain.dto.UserDetailsDto;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UserPasswordChangeDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.domain.dto.security.LoginDto;
import com.github.benchdoos.meetroom.domain.enumirations.AvatarDataType;
import com.github.benchdoos.meetroom.domain.interfaces.UserInfo;
import com.github.benchdoos.meetroom.exceptions.AdminCanNotRemoveAdminRoleForHimself;
import com.github.benchdoos.meetroom.exceptions.IllegalUserCredentialsException;
import com.github.benchdoos.meetroom.exceptions.OnlyAccountOwnerCanChangePassword;
import com.github.benchdoos.meetroom.exceptions.PasswordResetRequestExpired;
import com.github.benchdoos.meetroom.exceptions.PasswordResetRequestIsNotActiveAnyMore;
import com.github.benchdoos.meetroom.exceptions.PasswordResetRequestNotFoundException;
import com.github.benchdoos.meetroom.exceptions.UserAlreadyExistsException;
import com.github.benchdoos.meetroom.exceptions.UserCanNotUpdateThisDataByHimself;
import com.github.benchdoos.meetroom.exceptions.UserDisabledException;
import com.github.benchdoos.meetroom.exceptions.UserNotFoundException;
import com.github.benchdoos.meetroom.exceptions.UserWithSuchUsernameAlreadyExists;
import com.github.benchdoos.meetroom.mappers.UserMapper;
import com.github.benchdoos.meetroom.repository.PasswordResetRequestRepository;
import com.github.benchdoos.meetroom.repository.RoleRepository;
import com.github.benchdoos.meetroom.repository.UserRepository;
import com.github.benchdoos.meetroom.service.AvatarGeneratorService;
import com.github.benchdoos.meetroom.service.UserService;
import com.github.benchdoos.meetroom.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * Default implementation for {@link UserService}
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private static final int RANDOM_PASSWORD_LENGTH = 10;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordResetRequestRepository passwordResetRequestRepository;
    private final PasswordEncoder passwordEncoder;
    private final AvatarGeneratorService avatarGeneratorService;
    private final InternalConfiguration internalConfiguration;

    @Override
    public UserPublicInfoDto getUserPublicInfoDtoByUsername(String username) {
        final User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        final UserPublicInfoDto userPublicInfoDto = new UserPublicInfoDto();

        userMapper.convert(user, userPublicInfoDto);

        return userPublicInfoDto;
    }

    public UserExtendedInfoDto getUserExtendedInfoDtoByUsername(String username) {
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
    public UserExtendedInfoDto getUserExtendedInfoById(UUID id) {
        final User user = getUser(id);
        final UserExtendedInfoDto userExtendedInfoDto = new UserExtendedInfoDto();

        userMapper.convert(user, userExtendedInfoDto);

        return userExtendedInfoDto;
    }

    @Override
    public UserPublicInfoDto createUser(CreateUserDto createUserDto) {
        validateNewUser(createUserDto);

        final Role role = roleRepository.findFirstByInternalName(SecurityConstants.ROLE_USER);

        final Avatar avatar = prepareUserAvatar(createUserDto.getUsername());

        final User user = User.builder()
                .firstName(createUserDto.getFirstName())
                .lastName(createUserDto.getLastName())
                .username(createUserDto.getUsername())
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .roles(Collections.singleton(role))
                .avatar(avatar)
                .needActivation(false) //todo add email-activation, change to true
                .enabled(true)
                .build();

        final User savedUser = userRepository.save(user);
        log.info("Successfully created user with username: {}", savedUser.getUsername());
        final UserPublicInfoDto userPublicInfoDto = new UserPublicInfoDto();
        userMapper.convert(savedUser, userPublicInfoDto);

        return userPublicInfoDto;
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public void createOtherUser(CreateOtherUserDto createOtherUserDto) {
        validateNewUser(createOtherUserDto);

        final String password = generateRandomPassword(RANDOM_PASSWORD_LENGTH);

        final Role role = roleRepository.findFirstByInternalName(SecurityConstants.ROLE_USER);

        final Avatar avatar = prepareUserAvatar(createOtherUserDto.getUsername());

        final User userToSave = User.builder()
                .username(createOtherUserDto.getUsername())
                .firstName(createOtherUserDto.getFirstName())
                .lastName(createOtherUserDto.getLastName())
                .password(passwordEncoder.encode(password))
                .roles(Collections.singletonList(role))
                .avatar(avatar)
                .needActivation(true)
                .enabled(true)
                .build();

        userRepository.save(userToSave);
    }

    @Override
    public UserExtendedInfoDto editOtherUser(UUID id, EditOtherUserDto editOtherUserDto) {
        final User user = getUser(id);

        validateUsernameChange(editOtherUserDto, user);

        user.setUsername(editOtherUserDto.getUsername());
        user.setFirstName(editOtherUserDto.getFirstName());
        user.setLastName(editOtherUserDto.getLastName());

        final User savedUser = userRepository.save(user);

        final UserExtendedInfoDto userExtendedInfoDto = new UserExtendedInfoDto();
        userMapper.convert(user, userExtendedInfoDto);

        return userExtendedInfoDto;
    }

    @Override
    public UserExtendedInfoDto updateUserRoles(UUID id, EditRolesForUserDto editRolesForUserDto, Principal principal) {
        final User user = getUser(id);

        final List<Role> rolesByIds = roleRepository.findAllById(editRolesForUserDto.getRoles());

        validateAdminRoleChange(principal, user, rolesByIds);

        user.setRoles(rolesByIds);

        final User savedUser = userRepository.save(user);

        final UserExtendedInfoDto userExtendedInfoDto = new UserExtendedInfoDto();
        userMapper.convert(savedUser, userExtendedInfoDto);

        return userExtendedInfoDto;
    }

    @Transactional
    @Override
    public void callForUserPasswordReset(@NotNull UUID id, @NotNull Principal principal) {
        final ZonedDateTime requestTime = ZonedDateTime.now();

        final User user = getUser(id);

        final Collection<PasswordResetRequest> allActivePasswordResetRequests =
                passwordResetRequestRepository.findByRequestedForAndExpiresIsAfterAndActiveIsTrue(user, requestTime);

        if (!CollectionUtils.isEmpty(allActivePasswordResetRequests)) {
            allActivePasswordResetRequests.forEach(passwordResetRequest -> passwordResetRequest.setActive(false));
            passwordResetRequestRepository.saveAll(allActivePasswordResetRequests);
        }

        final User byUsername = userRepository.findByUsername(principal.getName()).orElseThrow(UserNotFoundException::new);

        final PasswordResetRequest passwordResetRequest = PasswordResetRequest.builder()
                .requestedBy(byUsername)
                .requestedFor(user)
                .active(true)
                .requested(requestTime)
                .expires(requestTime.plusDays(1))
                .build();

        passwordResetRequestRepository.save(passwordResetRequest);
    }

    @Override
    public void changeUserPassword(UUID id, UserPasswordChangeDto userPasswordChangeDto, Principal principal) {
        final User user = getUser(id);

        final User changer = userRepository.findByUsername(principal.getName()).orElseThrow(UserNotFoundException::new);

        if (!user.getId().equals(changer.getId())) {
            throw new OnlyAccountOwnerCanChangePassword(user.getUsername(), changer.getUsername());
        }

        user.setPassword(passwordEncoder.encode(userPasswordChangeDto.getPassword()));

        userRepository.save(user);
    }

    @Transactional
    @Override
    public void resetUserPasswordByResetRequest(UUID id, UserPasswordChangeDto userPasswordChangeDto) {
        final PasswordResetRequest passwordResetRequest = passwordResetRequestRepository.findById(id)
                .orElseThrow(PasswordResetRequestNotFoundException::new);

        if (!passwordResetRequest.isActive()) {
            throw new PasswordResetRequestIsNotActiveAnyMore();
        }

        if (ZonedDateTime.now().isAfter(passwordResetRequest.getExpires())) {
            throw new PasswordResetRequestExpired();
        }

        final User user = passwordResetRequest.getRequestedFor();
        user.setNeedActivation(false);

        user.setPassword(passwordEncoder.encode(userPasswordChangeDto.getPassword()));

        passwordResetRequest.setActive(false);

        passwordResetRequestRepository.save(passwordResetRequest);

        userRepository.save(user);
    }

    @Override
    public void updateUserEnable(@NotNull UUID id, boolean enabled, Principal principal) {
        final User user = getUser(id);

        if (principal != null) {
            if (principal.getName().equals(user.getUsername())) {
                throw new UserCanNotUpdateThisDataByHimself();
            }
        }

        user.setEnabled(enabled);

        userRepository.save(user);
    }

    /**
     * Validates if admin can change role for himself
     *
     * @param principal of user
     * @param user user to update
     * @param roles user roles
     */
    private void validateAdminRoleChange(Principal principal, User user, List<Role> roles) {
        final boolean hasAdminRoleInChange = roles.stream()
                .anyMatch(role -> role.getInternalName().equals(SecurityConstants.ROLE_ADMIN));
        if (!hasAdminRoleInChange) {
            final boolean userAdminRole = user.getRoles().stream()
                    .anyMatch(role -> role.getInternalName().equals(SecurityConstants.ROLE_ADMIN));

            if (userAdminRole) {

                final boolean isCurrentUserEditingHimself = principal.getName().equals(user.getUsername());

                if (isCurrentUserEditingHimself) { //no need to check if user is admin
                    throw new AdminCanNotRemoveAdminRoleForHimself(user.getUsername());
                }
            }
        }
    }

    /**
     * Validates username change
     *
     * @param editOtherUserDto dto with username to change
     * @param user user from db
     */
    private void validateUsernameChange(EditOtherUserDto editOtherUserDto, User user) {

        if (!user.getUsername().equals(editOtherUserDto.getUsername())) {

            final Optional<User> byUsername = userRepository.findByUsername(editOtherUserDto.getUsername());

            if (byUsername.isPresent()) {
                if (!user.getId().equals(byUsername.get().getId())) {
                    throw new UserWithSuchUsernameAlreadyExists(editOtherUserDto.getUsername());
                }
            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can not find user by username: " + username));

        checkUserIsNotDisabled(user);

        final UserDetailsDto userDetailsDto = new UserDetailsDto(user);

        userDetailsDto.setAuthorities(UserUtils.getUserRolesFromGrantedAuthorities(user.getRoles()));

        return userDetailsDto;
    }

    /**
     * Validate user for creation
     *
     * @param userInfo dto with user
     */
    private void validateNewUser(UserInfo userInfo) {

        final Optional<User> byUsername = userRepository.findByUsername(userInfo.getUsername());

        if (byUsername.isPresent()) {
            throw new UserAlreadyExistsException(userInfo.getUsername());
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

    @Override
    public Page<User> searchByUsernameAndNames(String request, Pageable pageable) {
        return userRepository.findAll(prepareUserSearchByUsernameOrLastAndFirstNameSpecification(request), pageable);
    }

    @Override
    public UserDetails getUserByLoginDto(LoginDto loginDto) {
        final UserDetails user = loadUserByUsername(loginDto.getUsername());

        final String encodedPassword = passwordEncoder.encode(loginDto.getPassword());

        log.debug("e: [{}] [{}]", encodedPassword, user.getPassword());

        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return user;
        }

        throw new IllegalUserCredentialsException();
    }

    @Override
    public UserPublicInfoDto updateUserUsername(EditUserUsernameDto editUserUsernameDto) {

        if (ObjectUtils.nullSafeEquals(editUserUsernameDto.getOldUsername(), editUserUsernameDto.getNewUsername())) {
            return getUserPublicInfoDtoByUsername(editUserUsernameDto.getOldUsername());
        }

        validateNewUser(editUserUsernameDto::getNewUsername);

        final User byUsername = userRepository.findByUsername(editUserUsernameDto.getOldUsername())
                .orElseThrow(UserNotFoundException::new);

        byUsername.setUsername(editUserUsernameDto.getNewUsername());

        final User savedUser = userRepository.save(byUsername);
        final UserPublicInfoDto userPublicInfoDto = new UserPublicInfoDto();

        userMapper.convert(savedUser, userPublicInfoDto);

        return userPublicInfoDto;
    }

    @Override
    public String getAvatarForUserId(UUID id) {
        final User byId = getById(id);
        final UserPublicInfoDto userPublicInfoDto = new UserPublicInfoDto();
        userMapper.convert(byId, userPublicInfoDto);
        return userPublicInfoDto.getAvatar();
    }

    @Override
    public UserAvatarDto updateUserAvatar(UUID userId, UpdateUserAvatarDto updateUserAvatarDto) {
        final User user = getById(userId);

        validateAvatar(updateUserAvatarDto);

        if (user.getAvatar() != null) {
            user.getAvatar().setType(updateUserAvatarDto.getType());
            user.getAvatar().setData(updateUserAvatarDto.getData());
        }

        userRepository.save(user);

        final UserAvatarDto userAvatarDto = new UserAvatarDto();
        userMapper.convertAvatar(user.getAvatar(), userAvatarDto);

        return userAvatarDto;
    }

    /**
     * Validate avatar data by given avatar type
     *
     * @param updateUserAvatar dto to validate
     */
    private void validateAvatar(UpdateUserAvatarDto updateUserAvatar) {
        switch (updateUserAvatar.getType()) {
            case GRAVATAR:
                //validate email
                break;
            case BASE64:
                //validate base64
                break;
        }
    }

    /**
     * Random password generator
     *
     * @param length password length
     * @return password
     */
    private static String generateRandomPassword(int length) {
        final String symbols = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()_-";
        final Random random = new Random();

        final StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            stringBuilder.append(symbols.charAt(random.nextInt(symbols.length())));
        }

        return stringBuilder.toString();
    }

    /**
     * Get user or throw exception
     *
     * @param id user id
     * @return user
     */
    private User getUser(@NotNull UUID id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Create {@link Specification} to look for users with username or last or first name are like request.
     *
     * @return specification with needed queries
     */
    private Specification<User> prepareUserSearchByUsernameOrLastAndFirstNameSpecification(String request) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final String requestPattern = "%" + request.toUpperCase() + "%";

            final Predicate username = criteriaBuilder.like(criteriaBuilder.upper(root.get("username")), requestPattern);

            final Expression<String> lastNameAndFirstNameExpression = criteriaBuilder.upper(
                    criteriaBuilder.concat(
                            criteriaBuilder.concat(root.get("lastName"), " "),
                            root.get("firstName")
                    )
            );

            final Predicate lastNameAndFirstNamePredicate = criteriaBuilder.like(lastNameAndFirstNameExpression, requestPattern);

            final Expression<String> firstNameAndLastNameExpression = criteriaBuilder.upper(
                    criteriaBuilder.concat(
                            criteriaBuilder.concat(root.get("firstName"), " "),
                            root.get("lastName")
                    )
            );

            final Predicate firstNameAndLastNamePredicate = criteriaBuilder.like(firstNameAndLastNameExpression, requestPattern);

            return criteriaBuilder.or(username, firstNameAndLastNamePredicate, lastNameAndFirstNamePredicate);
        };
    }

    /**
     * Creates {@link Avatar} instance (in memory, not in database) by given username
     *
     * @param username as a keyword
     * @return avatar
     */
    private Avatar prepareUserAvatar(String username) {
        final String generatedAvatarString = avatarGeneratorService.generateAvatarForString(
                username,
                internalConfiguration.getUserSettings().getAvatarSize());
        return new Avatar(null, AvatarDataType.BASE64, generatedAvatarString);
    }

}
