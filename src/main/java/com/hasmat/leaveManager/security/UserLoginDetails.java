package com.hasmat.leaveManager.security;

import com.hasmat.leaveManager.constants.UserType;
import com.hasmat.leaveManager.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public class UserLoginDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    User user = new User();

    public UserLoginDetails(User user) {
        super();
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String userType = user.getUserType();

        if (UserType.MANAGER.equals(userType)) {
            return Collections.singleton(new SimpleGrantedAuthority(UserType.MANAGER));
        } else if (UserType.ADMIN.equals(userType)) {
            return Collections.singleton(new SimpleGrantedAuthority(UserType.ADMIN));
        } else if (UserType.SUPER_ADMIN.equals(userType)) {
            return Collections.singleton(new SimpleGrantedAuthority(UserType.SUPER_ADMIN));
        } else {
            return Collections.singleton(new SimpleGrantedAuthority(UserType.EMPLOYEE));
        }
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmpId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
