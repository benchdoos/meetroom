package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.config.beans.SpringConfigurationInfoBean;
import com.github.benchdoos.meetroom.config.constants.SecurityConstants;
import com.github.benchdoos.meetroom.config.properties.InternalConfiguration;
import com.github.benchdoos.meetroom.domain.AccountActivationRequest;
import com.github.benchdoos.meetroom.domain.Avatar;
import com.github.benchdoos.meetroom.domain.PasswordResetRequest;
import com.github.benchdoos.meetroom.domain.Role;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.UserEmailUpdateRequest;
import com.github.benchdoos.meetroom.domain.dto.CreateOtherUserDto;
import com.github.benchdoos.meetroom.domain.dto.CreateUserDto;
import com.github.benchdoos.meetroom.domain.dto.EditOtherUserDto;
import com.github.benchdoos.meetroom.domain.dto.EditRolesForUserDto;
import com.github.benchdoos.meetroom.domain.dto.ResetUserPasswordDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserAvatarDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserEmailDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserPasswordDto;
import com.github.benchdoos.meetroom.domain.dto.UpdateUserUsernameDto;
import com.github.benchdoos.meetroom.domain.dto.UserAvatarDto;
import com.github.benchdoos.meetroom.domain.dto.UserDetailsDto;
import com.github.benchdoos.meetroom.domain.dto.UserExtendedInfoDto;
import com.github.benchdoos.meetroom.domain.dto.UserPublicInfoDto;
import com.github.benchdoos.meetroom.domain.dto.security.LoginDto;
import com.github.benchdoos.meetroom.domain.interfaces.UserInfo;
import com.github.benchdoos.meetroom.exceptions.AdminCanNotRemoveAdminRoleForHimselfException;
import com.github.benchdoos.meetroom.exceptions.EmailAlreadyExistsException;
import com.github.benchdoos.meetroom.exceptions.EmailIsAlreadyUsedException;
import com.github.benchdoos.meetroom.exceptions.EmailMustBeSetException;
import com.github.benchdoos.meetroom.exceptions.IllegalUserCredentialsException;
import com.github.benchdoos.meetroom.exceptions.InvalidCurrentPasswordException;
import com.github.benchdoos.meetroom.exceptions.OnlyAccountOwnerCanChangePasswordException;
import com.github.benchdoos.meetroom.exceptions.PasswordResetRequestExpiredException;
import com.github.benchdoos.meetroom.exceptions.PasswordResetRequestIsNotActiveAnyMoreException;
import com.github.benchdoos.meetroom.exceptions.UserAlreadyExistsException;
import com.github.benchdoos.meetroom.exceptions.UserCanNotUpdateThisDataByHimselfException;
import com.github.benchdoos.meetroom.exceptions.UserDisabledException;
import com.github.benchdoos.meetroom.exceptions.UserNotFoundException;
import com.github.benchdoos.meetroom.mappers.UserMapper;
import com.github.benchdoos.meetroom.repository.PasswordResetRequestRepository;
import com.github.benchdoos.meetroom.repository.RoleRepository;
import com.github.benchdoos.meetroom.repository.UserRepository;
import com.github.benchdoos.meetroom.service.AccountActivationService;
import com.github.benchdoos.meetroom.service.AvatarGeneratorService;
import com.github.benchdoos.meetroom.service.EmailService;
import com.github.benchdoos.meetroom.service.PasswordResetRequestService;
import com.github.benchdoos.meetroom.service.UserEmailUpdateService;
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
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Default implementation for {@link UserService}
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private static final int RANDOM_PASSWORD_LENGTH = 10;
    private final UserRepository userRepository;
    private final PasswordResetRequestRepository passwordResetRequestRepository;
    private final RoleRepository roleRepository;
    private final PasswordResetRequestService passwordResetRequestService;
    private final AvatarGeneratorService avatarGeneratorService;
    private final AccountActivationService accountActivationService;
    private final UserEmailUpdateService emailUpdateService;
    private final EmailService emailService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final InternalConfiguration internalConfiguration;
    private final SpringConfigurationInfoBean configurationInfoBean;

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

    @Override
    public UserPublicInfoDto getUserPublicInfoDtoByUsername(String username) {
        final User user = userRepository.findFirstByUsername(username).orElseThrow(UserNotFoundException::new);
        final UserPublicInfoDto userPublicInfoDto = new UserPublicInfoDto();

        userMapper.convert(user, userPublicInfoDto);

        return userPublicInfoDto;
    }

    public UserExtendedInfoDto getUserExtendedInfoDtoByUsername(String username) {
        final User user = userRepository.findFirstByUsername(username).orElseThrow(UserNotFoundException::new);
        final UserExtendedInfoDto userExtendedInfoDto = new UserExtendedInfoDto();

        userMapper.convert(user, userExtendedInfoDto);

        return userExtendedInfoDto;
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserExtendedInfoDto getUserExtendedInfoById(UUID id) {
        final User user = getUserById(id);
        final UserExtendedInfoDto userExtendedInfoDto = new UserExtendedInfoDto();

        userMapper.convert(user, userExtendedInfoDto);

        return userExtendedInfoDto;
    }

    @Override
    public UserPublicInfoDto getUserPublicInfoDtoById(UUID userId) {
        final User user = getUserById(userId);

        final UserPublicInfoDto userPublicInfoDto = new UserPublicInfoDto();
        userMapper.convert(user, userPublicInfoDto);
        return userPublicInfoDto;
    }

    @Override
    public UserPublicInfoDto createUser(CreateUserDto createUserDto) {
        //todo add validation for passwords?
        validateNewUser(createUserDto);

        final Role role = roleRepository.findFirstByInternalName(SecurityConstants.ROLE_USER);

        final Avatar avatar = prepareUserAvatar(createUserDto.getUsername());

        final User user = User.builder()
                .firstName(createUserDto.getFirstName())
                .lastName(createUserDto.getLastName())
                .username(createUserDto.getUsername())
                .email(createUserDto.getEmail())
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .roles(Collections.singleton(role))
                .avatar(avatar)
                .needActivation(true)
                .enabled(true)
                .build();

        final User savedUser = userRepository.save(user);
        log.info("Successfully created user with username: {}", savedUser.getUsername());

        final String publicFullApplicationUrl = configurationInfoBean.getPublicFullApplicationUrl();

        final AccountActivationRequest accountActivationRequest = accountActivationService.createAccountActivationRequest(savedUser);
        emailService.sendAccountActivation(publicFullApplicationUrl, accountActivationRequest);

        final UserPublicInfoDto userPublicInfoDto = new UserPublicInfoDto();
        userMapper.convert(savedUser, userPublicInfoDto);

        return userPublicInfoDto;
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
                .email(createOtherUserDto.getEmail())
                .password(passwordEncoder.encode(password))
                .roles(Collections.singletonList(role))
                .avatar(avatar)
                .needActivation(true)
                .enabled(true)
                .build();

        final User save = userRepository.save(userToSave);

        final AccountActivationRequest accountActivationRequest = accountActivationService.createAccountActivationRequest(save);

        emailService.sendAccountActivation(configurationInfoBean.getPublicFullApplicationUrl(), accountActivationRequest);
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public UserExtendedInfoDto updateOtherUser(UUID id, EditOtherUserDto editOtherUserDto) {
        final User user = getUserById(id);

        validateUsernameChange(editOtherUserDto, user);
        validateEmailChange(editOtherUserDto.getEmail(), user);

        user.setUsername(editOtherUserDto.getUsername());
        user.setFirstName(editOtherUserDto.getFirstName());
        user.setLastName(editOtherUserDto.getLastName());

        if (StringUtils.hasText(editOtherUserDto.getEmail())) {
            //empty emails are not pretty valid
            user.setEmail(editOtherUserDto.getEmail());
        }

        final User savedUser = userRepository.save(user);

        final UserExtendedInfoDto userExtendedInfoDto = new UserExtendedInfoDto();
        userMapper.convert(user, userExtendedInfoDto);

        return userExtendedInfoDto;
    }

    @Override
    public UserExtendedInfoDto updateUserRoles(UUID id, EditRolesForUserDto editRolesForUserDto, Principal principal) {
        final User user = getUserById(id);

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

        final User user = getUserById(id);

        final User byUsername = userRepository.findFirstByUsername(principal.getName()).orElseThrow(UserNotFoundException::new);

        final PasswordResetRequest saved = passwordResetRequestService.createPasswordResetRequest(byUsername, user, requestTime);

        if (StringUtils.hasText(user.getEmail())) {
            emailService.sendResetPasswordNotification(configurationInfoBean.getPublicFullApplicationUrl(), saved);
        }
    }

    @Override
    public void updateUserPassword(UUID id, UpdateUserPasswordDto updateUserPasswordDto, Principal principal) {
        final User user = getUserById(id);

        final boolean owner = UserUtils.checkPrincipalToGivenId(principal, id);

        if (!owner) {
            throw new OnlyAccountOwnerCanChangePasswordException(user.getUsername(), principal.getName());
        }

        final boolean matches = passwordEncoder.matches(updateUserPasswordDto.getCurrentPassword(), user.getPassword());

        if (!matches) {
            throw new InvalidCurrentPasswordException();
        }

        user.setPassword(passwordEncoder.encode(updateUserPasswordDto.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void resetUserPasswordByResetRequest(UUID id, ResetUserPasswordDto resetUserPasswordDto) {
        final PasswordResetRequest passwordResetRequest = passwordResetRequestService.getById((id));

        if (!passwordResetRequest.isActive()) {
            throw new PasswordResetRequestIsNotActiveAnyMoreException();
        }

        if (ZonedDateTime.now().isAfter(passwordResetRequest.getExpires())) {
            throw new PasswordResetRequestExpiredException();
        }

        final User user = passwordResetRequest.getRequestedFor();
        user.setNeedActivation(false); //todo analyze, probably can be removed

        user.setPassword(passwordEncoder.encode(resetUserPasswordDto.getPassword()));

        passwordResetRequestService.deactivateRequest(passwordResetRequest);

        userRepository.save(user);
    }

    @Override
    public void updateUserEnable(@NotNull UUID id, boolean enabled, Principal principal) {
        final User user = getUserById(id);

        if (principal != null) {
            if (principal.getName().equals(user.getUsername())) {
                throw new UserCanNotUpdateThisDataByHimselfException();
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
                    throw new AdminCanNotRemoveAdminRoleForHimselfException(user.getUsername());
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

            final Optional<User> byUsername = userRepository.findFirstByUsername(editOtherUserDto.getUsername());

            if (byUsername.isPresent()) {
                if (!user.getId().equals(byUsername.get().getId())) {
                    throw new UserAlreadyExistsException(editOtherUserDto.getUsername());
                }
            }
        }
    }

    /**
     * Validate email change
     *
     * @param newEmailAddress new email to validate
     * @param user user from db
     */
    private void validateEmailChange(String newEmailAddress, User user) {
        if (newEmailAddress != null) {
            final Optional<User> firstByEmail = userRepository.findFirstByEmail(newEmailAddress);
            if (firstByEmail.isPresent()) {
                if (!user.getId().equals(firstByEmail.get().getId())) {
                    throw new EmailIsAlreadyUsedException();
                }
            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final User user = userRepository.findFirstByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can not find user by username: " + username));

        checkUserIsNotDisabled(user);

        final UserDetailsDto userDetailsDto = new UserDetailsDto(user);

        userDetailsDto.setAuthorities(UserUtils.getUserRolesFromGrantedAuthorities(user.getRoles()));

        return userDetailsDto;
    }

    /**
     * Validate user for creation. Can not be used to validate updating user
     *
     * @param userInfo dto with user
     */
    private void validateNewUser(UserInfo userInfo) {

        final Optional<User> byUsername = userRepository.findFirstByUsername(userInfo.getUsername());
        if (byUsername.isPresent()) {
            throw new UserAlreadyExistsException(userInfo.getUsername());
        }

        final Optional<User> byEmail = userRepository.findFirstByEmail(userInfo.getEmail());
        if (byEmail.isPresent()) {
            throw new EmailAlreadyExistsException(userInfo.getEmail());
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
    public UserPublicInfoDto updateUserUsername(UpdateUserUsernameDto updateUserUsernameDto) {

        if (ObjectUtils.nullSafeEquals(updateUserUsernameDto.getOldUsername(), updateUserUsernameDto.getNewUsername())) {
            return getUserPublicInfoDtoByUsername(updateUserUsernameDto.getOldUsername());
        }

        final Optional<User> firstByUsername = userRepository.findFirstByUsername(updateUserUsernameDto.getNewUsername());
        if (firstByUsername.isPresent()) {
            throw new UserAlreadyExistsException(firstByUsername.get().getUsername());
        }

        final User byUsername = userRepository.findFirstByUsername(updateUserUsernameDto.getOldUsername())
                .orElseThrow(UserNotFoundException::new);

        byUsername.setUsername(updateUserUsernameDto.getNewUsername());

        final User savedUser = userRepository.save(byUsername);
        final UserPublicInfoDto userPublicInfoDto = new UserPublicInfoDto();

        userMapper.convert(savedUser, userPublicInfoDto);

        return userPublicInfoDto;
    }

    @Override
    public UserAvatarDto getAvatarForUserId(UUID id) {
        final User byId = getUserById(id);
        final UserPublicInfoDto userPublicInfoDto = new UserPublicInfoDto();
        userMapper.convert(byId, userPublicInfoDto);
        return userPublicInfoDto.getAvatar();
    }

    @Override
    public UserAvatarDto updateUserAvatar(UUID userId, UpdateUserAvatarDto updateUserAvatarDto) {
        final User user = getUserById(userId);

        if (user.getAvatar() != null) {
            user.getAvatar().setType(updateUserAvatarDto.getType());
            user.getAvatar().setData(updateUserAvatarDto.getData());
        } else {
            final Avatar avatar = new Avatar(null, updateUserAvatarDto.getType(), updateUserAvatarDto.getData());
            user.setAvatar(avatar);
        }

        userRepository.save(user);

        final UserAvatarDto userAvatarDto = new UserAvatarDto();
        userMapper.convertAvatar(user.getAvatar(), userAvatarDto);

        return userAvatarDto;
    }

    @Override
    public UserPublicInfoDto updateUserInfo(UUID userId, UpdateUserInfoDto updateUserInfoDto) {
        final User user = getUserById(userId);

        user.setFirstName(updateUserInfoDto.getFirstName());
        user.setLastName(updateUserInfoDto.getLastName());

        final User saved = userRepository.save(user);

        final UserPublicInfoDto userPublicInfoDto = new UserPublicInfoDto();

        userMapper.convert(user, userPublicInfoDto);

        return userPublicInfoDto;
    }

    @Override
    public void updateUserEmail(UUID userId, UpdateUserEmailDto userEmailDto) {
        final User user = getUserById(userId);
        validateEmailChange(userEmailDto.getNewEmail(), user);
        userEmailDto.setNewEmail(userEmailDto.getNewEmail().toLowerCase());

        if (!userEmailDto.getNewEmail().equals(user.getEmail())) {
            final CompletableFuture<UserEmailUpdateRequest> emailUpdateRequest = emailUpdateService.createEmailUpdateRequest(user, userEmailDto.getNewEmail());

            CompletableFuture.allOf(emailUpdateRequest).join();
            try {
                emailService.sendEmailUpdateRequests(
                        configurationInfoBean.getPublicFullApplicationUrl(),
                        user.getEmail(),
                        userEmailDto.getNewEmail(),
                        emailUpdateRequest.get());

                log.debug("Emails has been sent");
            } catch (InterruptedException | ExecutionException e) {
                log.warn("Could not execute completable feature for sending", e);
            }
        }
    }

    @Override
    public void sendAccountActivationRequest(UUID userId) {
        final User user = getUserById(userId);

        if (!StringUtils.hasText(user.getEmail())) {
            throw new EmailMustBeSetException(user.getUsername());
        }

        final AccountActivationRequest accountActivationRequest = accountActivationService.createAccountActivationRequest(user);

        emailService.sendAccountActivation(
                configurationInfoBean.getPublicFullApplicationUrl(),
                accountActivationRequest);
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
        final UserAvatarDto userAvatarDto = avatarGeneratorService.generateAvatarForString(
                username,
                internalConfiguration.getUserSettings().getAvatarSize());
        return new Avatar(null, userAvatarDto.getType(), userAvatarDto.getSrc());
    }

}
