package com.hasmat.leaveManager.controller.impl;

import com.hasmat.leaveManager.controller.LeaveBalanceController;
import com.hasmat.leaveManager.model.Leave;
import com.hasmat.leaveManager.model.LeaveBalance;
import com.hasmat.leaveManager.model.LeaveCalendar;
import com.hasmat.leaveManager.service.LeaveBalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/leave-balance")
@CrossOrigin
@Slf4j
public class LeaveBalanceControllerImpl implements LeaveBalanceController {

    @Autowired
    LeaveBalanceService leaveBalanceService;

    @Override
    @GetMapping(value = "/{empId}")
    public ResponseEntity<LeaveBalance> getLeaveBalanceOfEmployee(@PathVariable String empId) {
        try {
            LeaveBalance leaveBalance = leaveBalanceService.getLeaveBalanceForEmployee(empId);

            if (leaveBalance != null) {
                log.info("Fetched leave balance for employee: "+empId);
                return ResponseEntity.ok(leaveBalance);
            } else {
                log.info("No balance leave record found for employee: "+empId);
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            log.error("Error getting balance leave for employee: "+empId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
