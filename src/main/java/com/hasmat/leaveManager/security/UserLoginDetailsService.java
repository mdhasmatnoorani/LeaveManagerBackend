package com.hasmat.leaveManager.security;

import com.hasmat.leaveManager.model.User;
import com.hasmat.leaveManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@Service
public class UserLoginDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String empId) throws UsernameNotFoundException {
        User user = userRepository.findByEmpId(empId).orElseThrow(()->new UsernameNotFoundException("User not found : " + empId));
        return new UserLoginDetails(user);
    }
}
