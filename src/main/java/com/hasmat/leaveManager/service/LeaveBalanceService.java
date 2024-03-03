package com.hasmat.leaveManager.service;

import com.hasmat.leaveManager.model.LeaveBalance;

public interface LeaveBalanceService {
    LeaveBalance getLeaveBalanceForEmployee(String empId);
}
