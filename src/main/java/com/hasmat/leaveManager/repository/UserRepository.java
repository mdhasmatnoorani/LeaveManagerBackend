package com.hasmat.leaveManager.repository;

import com.hasmat.leaveManager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserEmail(String userEmail);

    boolean existsByUserEmail(String userEmail);

    Optional<User> findByEmpId(String empId);

    boolean existsByEmpId(String empId);

    List<User> findByUserDepartment(String department);
}
