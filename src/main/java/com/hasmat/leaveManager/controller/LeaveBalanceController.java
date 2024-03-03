package com.hasmat.leaveManager.controller;

import com.hasmat.leaveManager.model.LeaveBalance;
import org.springframework.http.ResponseEntity;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public interface LeaveBalanceController {
    ResponseEntity<LeaveBalance> getLeaveBalanceOfEmployee(String empId);
}
