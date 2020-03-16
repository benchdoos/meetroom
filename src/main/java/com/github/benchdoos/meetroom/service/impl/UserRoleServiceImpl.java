package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.config.properties.ProtectedDataProperties;
import com.github.benchdoos.meetroom.domain.Privilege;
import com.github.benchdoos.meetroom.domain.UserRole;
import com.github.benchdoos.meetroom.domain.dto.EditUserRoleDto;
import com.github.benchdoos.meetroom.exceptions.ProtectedRoleException;
import com.github.benchdoos.meetroom.exceptions.UserRoleNotFoundException;
import com.github.benchdoos.meetroom.repository.UserRoleRepository;
import com.github.benchdoos.meetroom.service.PrivilegeService;
import com.github.benchdoos.meetroom.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

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

    /**
     * Check if given role is not marked as protected
     * @param role role
     */
    private void checkIfRoleIsProtected(UserRole role) {

        if (!CollectionUtils.isEmpty(protectedDataProperties.getRoles())) {

            final boolean isRoleProtected = protectedDataProperties.getRoles().stream()
                    .anyMatch(roleInternalName -> roleInternalName.equals(role.getRole()));

            if (isRoleProtected) {
                throw new ProtectedRoleException(role.getRole());
            }
        }
    }
}
