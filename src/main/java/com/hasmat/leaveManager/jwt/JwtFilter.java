package com.hasmat.leaveManager.jwt;

import com.hasmat.leaveManager.security.UserLoginDetails;
import com.hasmat.leaveManager.security.UserLoginDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtils;

    @Autowired
    private UserLoginDetailsService userLoginDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String auth = request.getHeader("Authorization");
        String token = null;
        String empId = null;
        if (auth != null && auth.startsWith("Bearer ")) {
            token = auth.substring(7);
            empId = jwtUtils.getUsernameFromToken(token);
        }

        if (empId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserLoginDetails currentUser = (UserLoginDetails) userLoginDetailsService.loadUserByUsername(empId);

            UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(currentUser,
                    null, currentUser.getAuthorities());
            userAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(userAuthToken);
        }
        if (request.getRequestURI().contains("/leave") || request.getRequestURI().contains("/leave") ){
            request.setAttribute("empId", empId);
        }
        filterChain.doFilter(request, response);
    }
}

