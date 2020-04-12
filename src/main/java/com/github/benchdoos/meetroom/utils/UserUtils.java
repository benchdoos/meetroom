package com.github.benchdoos.meetroom.utils;

import com.github.benchdoos.meetroom.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User utilities
 */
public class UserUtils {

    /**
     * Transforms list of {@link Role} to {@link GrantedAuthority}
     *
     * @param roles list of user's roles
     * @return list of granted authorities
     */
    public static List<GrantedAuthority> getUserRolesFromGrantedAuthorities(Collection<Role> roles) {
        final List<GrantedAuthority> grantList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            roles.forEach(role -> {
                grantList.add(new SimpleGrantedAuthority(role.getInternalName()));
                role.getPrivileges().forEach(privilege -> grantList.add(new SimpleGrantedAuthority(privilege.getName())));
            });
        }
        return grantList;
    }

    /**
     * Transforms list of {@link GrantedAuthority} to  {@link Role}. Leaves only authorities starting with ROLE_
     *
     * @param roles list of user's roles
     * @return list of granted authorities
     */
    public static Collection<Role> getUserRolesFromGrantedAuthorities(List<GrantedAuthority> roles) {
        final List<Role> roleList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(roles)) {
            final List<Role> authorityRoles = roles.stream()
                    .filter(authority -> authority.getAuthority().startsWith("ROLE_"))
                    .map(authority -> Role.builder().internalName(authority.getAuthority()).build())
                    .collect(Collectors.toList());

            roleList.addAll(authorityRoles);
        }

        return roleList;
    }
}
