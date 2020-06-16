package com.bk.donglt.patient_manager.config.sercurity;

import com.bk.donglt.patient_manager.dto.user.UserDataDto;
import com.bk.donglt.patient_manager.dto.user.UserDetailDto;
import com.bk.donglt.patient_manager.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    @Getter
    @Setter
    private UserDetailDto user;

    public CustomUserDetails(UserDetailDto user, String password, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), password, authorities);
        this.user = user;
    }

    public boolean hasRole(Role role) {
        String authority = "ROLE_" + role.toString();
        return this.getAuthorities().stream().anyMatch(authorityInstance -> authorityInstance.getAuthority().equalsIgnoreCase(authority));
    }

    public void updateUser(UserDataDto userDto) {
        user.setName(userDto.getName());
        user.setDob(userDto.getDob());
        user.setAvatar(userDto.getAvatar());
        user.setGender(userDto.getGender());
        user.setAddress(userDto.getAddress());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
    }
}
