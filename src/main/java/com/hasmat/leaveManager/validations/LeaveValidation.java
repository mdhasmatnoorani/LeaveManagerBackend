package com.hasmat.leaveManager.validations;

import com.hasmat.leaveManager.model.Leave;
import com.hasmat.leaveManager.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@Component
@Slf4j
public class LeaveValidation {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Leave> validateLeave(Leave leave, String empId) {
        try {
            // Validate inputs
            if (leave == null || empId == null || empId.isEmpty()) {
                log.error("Invalid input: leave or empId is null or empty");
                return ResponseEntity.badRequest().build();
            }

            // Check if empId exists in the database
            if (!userRepository.existsByEmpId(empId)) {
                log.error("Employee with empId: " + empId + " does not exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Validate leaveTill is after leaveFrom
            if (leave.getLeaveFrom() != null && leave.getLeaveTill() != null
                    && leave.getLeaveTill().isBefore(leave.getLeaveFrom())) {
                log.error("Invalid leave dates: leaveTill must be after leaveFrom");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            // If all validations pass, return null to indicate success
            return null;
        } catch (Exception e) {
            log.error("Error validating leave", e);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
