package com.github.benchdoos.meetroom.domain.dto;

import com.github.benchdoos.meetroom.domain.User;
import com.github.benchdoos.meetroom.utils.AuthoritiesUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Dto with User Authorities and {@link UserDetails} info
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDetailsDto extends User implements UserDetails {
    private List<GrantedAuthority> authorities;

    public UserDetailsDto(User user) {
        BeanUtils.copyProperties(user, this);
        authorities = AuthoritiesUtils.getGrantedAuthoritiesFromUserRoles(this.getRoles());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }
}
