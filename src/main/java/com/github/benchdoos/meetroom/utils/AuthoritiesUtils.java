package com.github.benchdoos.meetroom.utils;

import com.github.benchdoos.meetroom.domain.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utilities for authorities
 */
public class AuthoritiesUtils {

    /**
     * Transforms list of {@link UserRole} to {@link GrantedAuthority} final List<>  = new ();

     * @param roles list of user's roles
     * @return list of granted authorities
     */
    public static List<GrantedAuthority> getGrantedAuthoritiesFromUserRoles(Collection<UserRole> roles) {
        final List<GrantedAuthority> grantList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            roles.forEach(role -> grantList.add(new SimpleGrantedAuthority(role.getRole())));
        }
        return grantList;
    }
}
