package com.github.benchdoos.meetroom.utils;

import com.github.benchdoos.meetroom.domain.Role;
import com.github.benchdoos.meetroom.domain.dto.UserDetailsDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
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

    /**
     * Check if given principal has any of given authorities
     *
     * @param principal user principal
     * @param authorities authorities
     * @return true if has, otherwise false
     */
    public static boolean hasAnyAuthority(@NotNull Principal principal, @NotEmpty String... authorities) {
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            final List<String> userStringAuthorities = ((UsernamePasswordAuthenticationToken) principal).getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return CollectionUtils.containsAny(userStringAuthorities, Arrays.asList(authorities));
        }
        return false;
    }

    /**
     * Check if principal has the same id to given
     *
     * @param principal principal
     * @param userId id of user
     * @return true if id are equal, otherwise - false
     */
    public static boolean checkPrincipalToGivenId(Principal principal, UUID userId) {
        try {
            if (principal != null) {
                if (principal instanceof UsernamePasswordAuthenticationToken) {
                    final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
                    if (token.getPrincipal() instanceof UserDetailsDto) {
                        final UUID principalId = ((UserDetailsDto) token.getPrincipal()).getId();
                        return userId.equals(principalId);
                    }
                }
            }
        } catch (final Exception ignore) {
            /*NOP*/
        }
        return false;
    }
}
