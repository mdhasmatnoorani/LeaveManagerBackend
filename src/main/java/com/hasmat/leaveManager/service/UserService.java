package com.hasmat.leaveManager.service;

import com.hasmat.leaveManager.model.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public interface UserService {
    User createUser(User user);
    Optional<User> fetchUserById(String id);
    Long getNumberOfUsers();
    List<User> getAllUsers();
    Optional<User> getUserByEmpId(String empId);
    byte[] generateExcelForAllUsers();
}
