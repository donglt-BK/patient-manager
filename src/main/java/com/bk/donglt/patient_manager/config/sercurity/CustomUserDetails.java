package com.bk.donglt.patient_manager.config.sercurity;

import com.bk.donglt.patient_manager.enums.Role;
import com.bk.donglt.patient_manager.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private User user;

    public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }

    public boolean hasRole(Role role) {
        String authority = "ROLE_" + role.toString();
        return this.getAuthorities().stream().anyMatch(authorityInstance -> authorityInstance.getAuthority().equalsIgnoreCase(authority));
    }

    public boolean hasAnyRole(Role... roles) {
        for (Role role : roles) {
            if (hasRole(role)) {
                return true;
            }
        }
        return false;
    }

    public User getUser() {
        return user;
    }
}
