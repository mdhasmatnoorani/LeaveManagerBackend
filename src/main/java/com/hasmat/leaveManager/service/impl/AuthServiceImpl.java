package com.hasmat.leaveManager.service.impl;

import com.hasmat.leaveManager.jwt.JwtUtil;
import com.hasmat.leaveManager.model.User;
import com.hasmat.leaveManager.response.AuthResponse;
import com.hasmat.leaveManager.security.UserLoginDetails;
import com.hasmat.leaveManager.security.UserLoginDetailsService;
import com.hasmat.leaveManager.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserLoginDetailsService userLoginDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public AuthResponse authenticateUser(User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmpId(), user.getUserPassword()));
            if (authentication.isAuthenticated()) {
                UserLoginDetails userLoginDetails = (UserLoginDetails) userLoginDetailsService.loadUserByUsername(user.getEmpId());
                String token = jwtUtil.generateJwtToken(userLoginDetails);
                String role = extractUserRole(userLoginDetails);
                String username = user.getEmpId();
                log.info("User logged in with employee: " + username);
                return new AuthResponse(token, role, username);
            } else {
                log.warn("Authentication failed for employee: " + user.getUserId());
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    // Extract user role from UserLoginDetails
    private String extractUserRole(UserLoginDetails userLoginDetails) {
        Collection<? extends GrantedAuthority> authorities = userLoginDetails.getAuthorities();
        if (!authorities.isEmpty()) {
            return authorities.iterator().next().getAuthority();
        } else {
            return "Invalid Role";
        }
    }
}
