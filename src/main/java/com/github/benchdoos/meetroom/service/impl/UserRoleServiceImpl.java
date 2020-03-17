package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.config.properties.ProtectedDataProperties;
import com.github.benchdoos.meetroom.domain.Privilege;
import com.github.benchdoos.meetroom.domain.UserRole;
import com.github.benchdoos.meetroom.domain.dto.CreateUserRoleDto;
import com.github.benchdoos.meetroom.domain.dto.EditUserRoleDto;
import com.github.benchdoos.meetroom.exceptions.ProtectedRoleException;
import com.github.benchdoos.meetroom.exceptions.UserRoleAlreadyExistsException;
import com.github.benchdoos.meetroom.exceptions.UserRoleNotFoundException;
import com.github.benchdoos.meetroom.repository.UserRoleRepository;
import com.github.benchdoos.meetroom.service.PrivilegeService;
import com.github.benchdoos.meetroom.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final PrivilegeService privilegeService;
    private final ProtectedDataProperties protectedDataProperties;

    @Override
    public List<UserRole> getAllUserRoles(Sort sort) {
        return userRoleRepository.findAll(sort);
    }

    @Override
    public Page<UserRole> findAllUserRoles(Pageable pageable) {
        return userRoleRepository.findAll(pageable);
    }

    @Override
    public UserRole updateUserRole(UUID id, EditUserRoleDto editUserRoleDto) {
        final UserRole roleToUpdate = userRoleRepository.findById(id).orElseThrow(UserRoleNotFoundException::new);

        checkIfRoleIsProtected(roleToUpdate);

        final List<Privilege> privileges = privilegeService.findAllByIds(editUserRoleDto.getPrivileges());

        roleToUpdate.setName(editUserRoleDto.getName());
        roleToUpdate.setRole(editUserRoleDto.getRole());
        roleToUpdate.setPrivileges(privileges);

        return userRoleRepository.save(roleToUpdate);
    }

    @Override
    public UserRole createUserRole(CreateUserRoleDto createUserRoleDto) {
        final Optional<UserRole> firstByRole = userRoleRepository.findFirstByRoleOrName(
                createUserRoleDto.getRole(),
                createUserRoleDto.getName()
        );

        if (firstByRole.isPresent()) {
            throw new UserRoleAlreadyExistsException(createUserRoleDto.getName(), createUserRoleDto.getRole());
        }

        final List<Privilege> privileges = privilegeService.findAllByIds(createUserRoleDto.getPrivileges());

        final UserRole userRole = UserRole.builder()
                .name(createUserRoleDto.getName())
                .role(createUserRoleDto.getRole())
                .privileges(privileges)
                .build();
        return userRoleRepository.save(userRole);
    }

    @Transactional
    @Override
    public void deleteRole(UUID id) {
        final UserRole userRole = userRoleRepository.findById(id).orElseThrow(UserRoleNotFoundException::new);

        log.info("User requested to delete role: {}", userRole.getRole());

        checkIfRoleIsProtected(userRole);

        log.info("Deleting role {}", userRole.getRole());

        userRoleRepository.delete(userRole);
    }

    /**
     * Check if given role is not marked as protected
     *
     * @param role role
     */
    private void checkIfRoleIsProtected(UserRole role) {
        log.debug("Checking if role is protected");

        if (!CollectionUtils.isEmpty(protectedDataProperties.getRoles())) {

            final boolean isRoleProtected = protectedDataProperties.getRoles().stream()
                    .anyMatch(roleInternalName -> roleInternalName.equals(role.getRole()));

            if (isRoleProtected) {

                log.warn("Role {} is protected.", role.getRole());

                throw new ProtectedRoleException(role.getRole());
            }
        }
    }
}
