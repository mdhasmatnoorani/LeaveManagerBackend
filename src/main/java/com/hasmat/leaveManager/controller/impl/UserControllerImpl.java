package com.hasmat.leaveManager.controller.impl;

import com.hasmat.leaveManager.controller.UserController;
import com.hasmat.leaveManager.model.User;
import com.hasmat.leaveManager.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@RestController
@RequestMapping(value = "/api/v1/user")
@CrossOrigin
@Slf4j
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;

    @Override
    @PostMapping(value = "/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            User addedUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
        } catch (Exception e) {
            log.error("Failed to add user. " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add user." + e.getMessage());
        }
    }

    @Override
    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        try {
            Optional<User> user = userService.fetchUserById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            log.error("Failed to fetch user details. " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch user details." +e.getMessage());
        }
    }

    @Override
    @GetMapping("/total-employees")
    public ResponseEntity<?> getNumberOfTotalEmployees() {
        try {
            Long numberOfTotalEmployees = userService.getNumberOfUsers();
            return ResponseEntity.status(HttpStatus.OK).body(numberOfTotalEmployees);
        } catch (Exception e) {
            log.error("Failed to fetch total number of users ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch total number of users " + e.getMessage());
        }
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<?> getAllEmployees() {
        try {
            List<User> allUsers = userService.getAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body(allUsers);
        } catch (Exception e) {
            log.error("Failed to fetch all user details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch all user details " + e.getMessage());
        }
    }

    @Override
    @GetMapping("/fetch/{empId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable String empId) {
        try {
            Optional<User> user = userService.getUserByEmpId(empId);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            log.error("Failed to fetch user for emp id: " + empId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch user for emp id: " + empId + e.getMessage());
        }
    }

    @Override
    @GetMapping(value = "/download-excel")
    public ResponseEntity<byte[]> generateExcelForAllUsers(HttpServletResponse response) {
        try {
            byte[] excelBytes = userService.generateExcelForAllUsers();

            if (excelBytes.length > 0) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", "user_data.xlsx");
                log.info("Excel generated successfully for all users");
                return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            log.error("Error generating Excel file for all users data: " , e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
