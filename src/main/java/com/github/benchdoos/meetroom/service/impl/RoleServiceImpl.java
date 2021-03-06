package com.github.benchdoos.meetroom.service.impl;

import com.github.benchdoos.meetroom.config.properties.ProtectedDataProperties;
import com.github.benchdoos.meetroom.domain.Privilege;
import com.github.benchdoos.meetroom.domain.Role;
import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.domain.dto.CreateRoleDto;
import com.github.benchdoos.meetroom.domain.dto.EditRoleDto;
import com.github.benchdoos.meetroom.exceptions.ProtectedRoleException;
import com.github.benchdoos.meetroom.exceptions.RoleAlreadyExistsException;
import com.github.benchdoos.meetroom.exceptions.RoleNotFoundException;
import com.github.benchdoos.meetroom.repository.RoleRepository;
import com.github.benchdoos.meetroom.repository.UserRepository;
import com.github.benchdoos.meetroom.service.PrivilegeService;
import com.github.benchdoos.meetroom.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PrivilegeService privilegeService;
    private final ProtectedDataProperties protectedDataProperties;
    private final UserRepository userRepository;

    @Override
    public List<Role> getAllRoles(Sort sort) {
        return roleRepository.findAll(sort);
    }

    @Override
    public Page<Role> findAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public Role getRole(UUID id) {
        return roleRepository.findById(id).orElseThrow(RoleNotFoundException::new);
    }

    @Override
    public Role updateRole(UUID id, EditRoleDto editRoleDto) {
        final Role roleToUpdate = roleRepository.findById(id).orElseThrow(RoleNotFoundException::new);

        checkIfRoleIsProtected(roleToUpdate);

        final String color = getValidColor(editRoleDto.getColor());

        final List<Privilege> privileges = privilegeService.findAllByIds(editRoleDto.getPrivileges());

        roleToUpdate.setName(editRoleDto.getName());
        roleToUpdate.setInternalName(editRoleDto.getInternalName());
        roleToUpdate.setPrivileges(privileges);
        roleToUpdate.setColor(color);

        return roleRepository.save(roleToUpdate);
    }

    @Override
    public Role createRole(CreateRoleDto createRoleDto) {
        final Optional<Role> firstByRole = roleRepository.findFirstByInternalNameOrName(
                createRoleDto.getInternalName(),
                createRoleDto.getName()
        );

        if (firstByRole.isPresent()) {
            throw new RoleAlreadyExistsException(createRoleDto.getName(), createRoleDto.getInternalName());
        }

        final String color = getValidColor(createRoleDto.getColor());

        final List<Privilege> privileges = privilegeService.findAllByIds(createRoleDto.getPrivileges());

        final Role role = Role.builder()
                .name(createRoleDto.getName())
                .internalName(createRoleDto.getInternalName())
                .privileges(privileges)
                .color(color)
                .build();
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void deleteRole(UUID id) {
        final Role role = roleRepository.findById(id).orElseThrow(RoleNotFoundException::new);

        log.info("User requested to delete role: {}", role.getInternalName());

        checkIfRoleIsProtected(role);

        log.info("Detaching users from role: {}", role.getInternalName());

        final Collection<User> users = userRepository.findAllByRolesIn(Collections.singletonList(role));

        if (!CollectionUtils.isEmpty(users)) {
            users.forEach(user -> user.getRoles().remove(role));
        }
        log.info("Detached users: {}", users.size());

        log.info("Deleting role {}", role.getInternalName());

        roleRepository.delete(role);
    }

    @Override
    public Collection<User> getTotalActiveUsers(Role role) {
        return userRepository.findAllByRolesInAndEnabledIsTrue(Collections.singletonList(role));
    }

    @Override
    public Page<Role> searchRolesByNames(String request, Pageable pageable) {
        return roleRepository.findAll(prepareSpecificationToSearchRolesByNameAndInternalName(request), pageable);
    }

    /**
     * Check if given role is not marked as protected
     *
     * @param role role
     */
    private void checkIfRoleIsProtected(Role role) {
        log.debug("Checking if role is protected");

        if (!CollectionUtils.isEmpty(protectedDataProperties.getRoles())) {

            final boolean isRoleProtected = protectedDataProperties.getRoles().stream()
                    .anyMatch(roleInternalName -> roleInternalName.equals(role.getInternalName()));

            if (isRoleProtected) {

                log.warn("Role {} is protected.", role.getInternalName());

                throw new ProtectedRoleException(role.getInternalName());
            }
        }
    }

    /**
     * Gets valid color for color value
     * todo: move to Jackson deserialization if possible
     *
     * @param color in hex or empty string
     * @return color in hex, or null if empty string
     */
    private String getValidColor(String color) {
        return StringUtils.hasText(color) ? color : null;
    }

    /**
     * Prepare specification to search roles by name and internal name
     *
     * @param request request
     * @return specification
     */
    private Specification<Role> prepareSpecificationToSearchRolesByNameAndInternalName(String request) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final String requestPattern = "%" + request.toUpperCase() + "%";
            final Predicate name = criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), requestPattern);
            final Predicate internalName = criteriaBuilder.like(criteriaBuilder.upper(root.get("internalName")), requestPattern);

            return criteriaBuilder.or(name, internalName);
        };
    }
}
