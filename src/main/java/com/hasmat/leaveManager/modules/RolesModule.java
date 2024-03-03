package com.hasmat.leaveManager.modules;

import com.hasmat.leaveManager.constants.UserType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RolesModule {
    public boolean hasManagerRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> UserType.MANAGER.equals(authority.getAuthority()));
    }

    public boolean hasAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> UserType.ADMIN.equals(authority.getAuthority()));
    }
}
