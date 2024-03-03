package com.hasmat.leaveManager.controller;

import com.hasmat.leaveManager.model.Leave;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public interface LeaveController {

    ResponseEntity<Leave> addLeave(Leave leave, String empId);
    ResponseEntity<Leave> approveLeave(String leaveId);
    ResponseEntity<Leave> rejectLeave(String leaveId);
    ResponseEntity<String> getLeaveStatus(String leaveId);
    ResponseEntity<List<Leave>> getLeavesByEmpId(String empId);
    ResponseEntity<byte[]> generateExcelForEmpId(String empId, HttpServletResponse response);
    ResponseEntity<byte[]> generateExcelForLeaves(HttpServletResponse response);
    ResponseEntity<List<Leave>> getApprovedLeaves();
    ResponseEntity<List<Leave>> getRejectedLeaves();
    ResponseEntity<?> getApprovedLeavesCount();
    ResponseEntity<?> getRejectedLeavesCount();
    ResponseEntity<?> getAllLeaves();
    ResponseEntity<?> getAllLeavesCount();

}
