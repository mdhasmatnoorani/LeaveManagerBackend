package com.hasmat.leaveManager.service;

import com.hasmat.leaveManager.model.Leave;

import java.util.List;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public interface LeaveService {
    Leave createLeave(Leave leave, String empId);
    Leave approveLeave(String leaveId);
    Leave rejectLeave(String leaveId);
    String getLeaveStatus(String leaveId);
    List<Leave> getLeaveByEmpId(String empId);
    byte[] generateExcelForEmpId(String empId);
    byte[] generateExcelForLeaves();
    List<Leave> getApprovedLeaves();
    List<Leave> getRejectedLeaves();
    Long getApprovedLeavesCount();
    Long getRejectedLeavesCount();
    List<Leave> getAllLeaves();
    Long getAllLeavesCount();

}
