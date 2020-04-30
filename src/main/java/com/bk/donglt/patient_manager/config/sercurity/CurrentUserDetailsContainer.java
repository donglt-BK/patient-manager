package com.bk.donglt.patient_manager.config.sercurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserDetailsContainer {
    public CustomUserDetails getUserDetails() {
        if (SecurityContextHolder.getContext() != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {

                Object principal = authentication.getPrincipal();
                if (principal instanceof CustomUserDetails) {
                    return (CustomUserDetails) principal;
                }
            }
        }
        return null;
    }

}
