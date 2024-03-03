package com.hasmat.leaveManager.controller;

import com.hasmat.leaveManager.model.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public interface UserController {
    ResponseEntity<?> addUser(User user);
    ResponseEntity<?> getUserById(String userId);
    ResponseEntity<?> getNumberOfTotalEmployees();
    ResponseEntity<?> getAllEmployees();
    ResponseEntity<?> getEmployeeById(String empId);
    ResponseEntity<byte[]> generateExcelForAllUsers(HttpServletResponse response);
}
