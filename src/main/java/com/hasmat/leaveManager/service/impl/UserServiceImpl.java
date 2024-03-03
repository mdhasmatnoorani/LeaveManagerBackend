package com.hasmat.leaveManager.service.impl;

import com.hasmat.leaveManager.constants.UserDepartment;
import com.hasmat.leaveManager.constants.UserType;
import com.hasmat.leaveManager.exceptions.UserAlreadyExistsException;
import com.hasmat.leaveManager.exceptions.UserNotFoundException;
import com.hasmat.leaveManager.model.Leave;
import com.hasmat.leaveManager.model.User;
import com.hasmat.leaveManager.modules.ExcelModule;
import com.hasmat.leaveManager.modules.RolesModule;
import com.hasmat.leaveManager.repository.UserRepository;
import com.hasmat.leaveManager.service.UserService;
import com.hasmat.leaveManager.utility.Duration;
import com.hasmat.leaveManager.utility.EmpIdGenerator;
import com.hasmat.leaveManager.utility.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmpIdGenerator empIdGenerator;

    @Autowired
    private ExcelModule excelModule;

    @Autowired
    private RolesModule rolesModule;

    @Override
    public User createUser(User user) {

        Timer timer = new Timer();

        // Check if user object is null
        if (user == null) {
            log.error("User cannot be null");
            throw new IllegalArgumentException("User cannot be null");
        }

        user.setEmpId(empIdGenerator.generateEmpId());
        if(!StringUtils.isEmpty(user.getUserId())) {
            // Check for existing user
            Optional<User> existingUser = userRepository.findById(user.getUserId());
            if (existingUser.isPresent()) {
                log.error("User already exists with id: " + user.getUserId());
                throw new UserAlreadyExistsException("User already exists with id: " + user.getUserId());
            }
        }
        // Check for existing empId for user
        if (userRepository.existsByEmpId(user.getEmpId())) {
            log.error("User already exists with EmployeeId: " + user.getEmpId());
            throw new UserAlreadyExistsException("User already exists with EmployeeId: " + user.getEmpId());
        }

        // Check for existing email for user
        if (userRepository.existsByUserEmail(user.getUserEmail())) {
            log.error("User already exists with EmailId: " + user.getUserEmail());
            throw new UserAlreadyExistsException("User already exists with EmailId: " + user.getUserEmail());
        }
        user.setEmpId(EmpIdGenerator.generateEmpId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encPass = encoder.encode(user.getUserPassword());
        user.setUserPassword(encPass);
        user.setUserType(UserType.EMPLOYEE);
        user.setUserDepartment(UserDepartment.IT);
        User savedUser = userRepository.save(user);
        log.info("User added successfully with id: " + savedUser.getUserId() + " in " + timer.getTime(Duration.Unit.SECONDS) + "seconds.");
        return savedUser;
    }


    @Override
    public Optional<User> fetchUserById(String id) {
        Timer timer = new Timer();
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            log.error("User not found with id: "+id);
            throw new UserNotFoundException("User not found with id: "+id);
        }
        log.info("User details fetched successfully for id: " + id + " in " + timer.getTime(Duration.Unit.SECONDS) + "seconds.");
        return user;
    }

    @Override
    public Long getNumberOfUsers() {
        Long numberOfTotalEmployees =  userRepository.count();
        log.info("Fetched number of total employees. Total number of Employees: "+numberOfTotalEmployees);
        return numberOfTotalEmployees;

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByEmpId(String empId) {
        return userRepository.findByEmpId(empId);
    }

    @Override
    public byte[] generateExcelForAllUsers() {
        //Check if user is manager
        if (!rolesModule.hasAdminRole()) {
            log.info("User does not have the required authorization to download users data");
            throw new SecurityException("User does not have the required authorization to download users data");
        }

        List<User> users = userRepository.findAll();

        // Create Excel file from the retrieved users
        if (!users.isEmpty()) {
            try {
                log.info("Excel generated for users data");
                return excelModule.createExcelBytesForUsers(users);
            } catch (IOException e) {
                log.error("Error creating Excel file for users data: ", e);
            }
        }

        return new byte[0];
    }
}
