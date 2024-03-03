package com.hasmat.leaveManager.service.impl;

import com.hasmat.leaveManager.model.LeaveBalance;
import com.hasmat.leaveManager.modules.RolesModule;
import com.hasmat.leaveManager.repository.LeaveBalanceRepository;
import com.hasmat.leaveManager.service.LeaveBalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

    @Autowired
    LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    RolesModule rolesModule;

    @Override
    public LeaveBalance getLeaveBalanceForEmployee(String empId) {
        return leaveBalanceRepository.findLeaveBalanceByEmpId(empId);
    }
}
