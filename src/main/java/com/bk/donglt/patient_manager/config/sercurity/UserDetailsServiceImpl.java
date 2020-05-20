package com.bk.donglt.patient_manager.config.sercurity;

import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    private final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Load user by username [{}] ", username);
        User user = userService.findUser(username);

        if (user == null) {
            throw new UsernameNotFoundException("Cannot find user " + username);
        }

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()));

        return new CustomUserDetails(userService.getUserInfo(user.getId()), user.getPassword(), authorities);
    }
}
