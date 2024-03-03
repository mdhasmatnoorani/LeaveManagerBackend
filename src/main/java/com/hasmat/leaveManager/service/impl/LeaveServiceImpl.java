package com.hasmat.leaveManager.service.impl;

import com.hasmat.leaveManager.constants.LeaveCategory;
import com.hasmat.leaveManager.constants.LeaveStatus;
import com.hasmat.leaveManager.constants.LeaveType;
import com.hasmat.leaveManager.model.Leave;
import com.hasmat.leaveManager.modules.ExcelModule;
import com.hasmat.leaveManager.modules.RolesModule;
import com.hasmat.leaveManager.repository.LeaveRepository;
import com.hasmat.leaveManager.service.LeaveService;
import com.hasmat.leaveManager.service.NotificationService;
import com.hasmat.leaveManager.utility.LeaveIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@Service
@Slf4j
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private ExcelModule excelModule;

    @Autowired
    private RolesModule rolesModule;

    @Autowired
    NotificationService notificationService;

    @Override
    public Leave createLeave(Leave leave, String empId) {
        LocalDate leaveFromDate = leave.getLeaveFrom().toLocalDate();
        LocalDate leaveToDate = leave.getLeaveTill().toLocalDate();

        if (leaveFromDate.isAfter(leaveToDate)) {
            log.warn("Invalid leave dates: " + leaveFromDate + " and " + leaveToDate + " .LeaveFrom must be before or equal to LeaveTill");
            throw new RuntimeException("Invalid leave dates: " + leaveFromDate + " and " + leaveToDate + " .LeaveFrom must be before or equal to LeaveTill");
        }

        // get leave from and leave till
        LocalDateTime leaveFromDateTime = leave.getLeaveFrom();
        LocalDateTime leaveToDateTime = leave.getLeaveTill();

        if (!leaveRepository.existsByLeaveFromBetween(leaveFromDateTime, leaveToDateTime)) {
            leave.setLeaveId(LeaveIdGenerator.generateLeaveId());
            leave.setLeaveType(LeaveType.FULL_DAY);
            leave.setLeaveCategory(LeaveCategory.CASUAL);
            leave.setEmpId(empId);
            leave.setIsApproved(LeaveStatus.PENDING);
            leave.setLastExecutionTime(LocalDateTime.now());
            return leaveRepository.save(leave);
        } else {
            throw new RuntimeException("Leave already exists for the given date");
        }
    }



    @Override
    public Leave approveLeave(String leaveId) {

        //Fetch leave by leave id
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new IllegalArgumentException("Leave not found with leaveId: " + leaveId)
                );

        //Check if user is manager
        if (!rolesModule.hasAdminRole()) {
            log.warn("User " + leave.getEmpId() + "User does not have the required authorization to approve leave");
            throw new SecurityException("User does not have the required authorization to approve leave");
        }


        // Check if leave is already approved
        if (LeaveStatus.APPROVED.equalsIgnoreCase(leave.getIsApproved())) {
            log.error("Leave is already approved for leaveId: " + leaveId + " for empId: " + leave.getEmpId());
            throw new IllegalStateException("Leave is already approved for leaveId: " + leaveId);
        }else if(LeaveStatus.REJECTED.equalsIgnoreCase(leave.getIsApproved())){
            log.error("This leave request has been rejected. Cannot approve rejected leave for leaveId: " + leaveId + " and empId: " + leave.getEmpId());
            throw new IllegalStateException("Rejected leave cannot be approved for leaveId: " + leaveId );
        }

        // Check if leave is in pending status
        if (leave.getIsApproved().equalsIgnoreCase(LeaveStatus.PENDING)) {
            leave.setIsApproved(LeaveStatus.APPROVED);
            leave.setLastExecutionTime(LocalDateTime.now());
            log.info("Leave approved for leave id: "+leaveId);
        }
        log.info("Leave approved for employee: " + leave.getEmpId() + " with leaveId " +leave.getLeaveId());
        Leave approvedLeave =  leaveRepository.save(leave);
        notificationService.notifyLeaveApproval(approvedLeave);
        return approvedLeave;
    }


    @Override
    public Leave rejectLeave(String leaveId) {

        //Fetch leave by leave id
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new IllegalArgumentException("Leave not found with leaveId: " + leaveId)
                );

        //Check if user is manager
        if (!rolesModule.hasAdminRole()) {
            log.warn("User " + leave.getEmpId() + "User does not have the required authorization to reject leave");
            throw new SecurityException("User does not have the required authorization to reject leave");
        }

        // Check if leave is already rejected
        if (LeaveStatus.REJECTED.equalsIgnoreCase(leave.getIsApproved())) {
            log.error("Leave is already rejected for leaveId: " + leaveId + " for empId: " + leave.getEmpId());
            throw new IllegalStateException("Leave is already rejected for leaveId: " + leaveId);
        }else if(LeaveStatus.APPROVED.equalsIgnoreCase(leave.getIsApproved())){
            log.error("This leave request has been approved. Cannot reject approved leave for leaveId: " + leaveId + " and empId: " + leave.getEmpId());
            throw new IllegalStateException("Approved leave cannot be rejected for leaveId: " + leaveId );
        }

        // Check if leave is in pending status
        if (leave.getIsApproved().equalsIgnoreCase(LeaveStatus.PENDING)) {
            leave.setIsApproved(LeaveStatus.REJECTED);
            leave.setLastExecutionTime(LocalDateTime.now());
            log.info("Leave rejected for leave id: "+leaveId);
        }
        log.info("Leave rejected for employee: " + leave.getEmpId() + " with leaveId " +leave.getLeaveId());
        Leave rejectedLeave =  leaveRepository.save(leave);
        notificationService.notifyLeaveRejection(rejectedLeave);
        return rejectedLeave;
    }

    @Override
    public String getLeaveStatus(String leaveId) {
        // Fetch the leave from the repository by leaveId
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new IllegalArgumentException("Leave not found with leaveId: " + leaveId));

        // Return the approval status
        return leave.getIsApproved();
    }

    @Override
    public List<Leave> getLeaveByEmpId(String empId) {
        return leaveRepository.findByEmpId(empId);
    }

    @Override
    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    @Override
    public Long getAllLeavesCount() {
        Long allLeavesCount = leaveRepository.count();
        log.info("Fetched number of total leaves. Total number of leaves: "+allLeavesCount);
        return allLeavesCount;
    }

    @Override
    public byte[] generateExcelForEmpId(String empId) {

        //Check if user is manager
        if (!rolesModule.hasAdminRole()) {
            log.info("User " + empId + " does not have the required authorization to download leave data");
            throw new SecurityException("User " + empId + " does not have the required authorization to download leave data");
        }

        List<Leave> leaves = leaveRepository.findByEmpId(empId);

        // Create Excel file from the retrieved leaves
        if (!leaves.isEmpty()) {
            try {
                log.info("Excel generated for employee: "+empId);
                return excelModule.createExcelBytes(empId, leaves);
            } catch (IOException e) {
                log.error("Error creating Excel file for empId: " + empId, e);
            }
        }

        return new byte[0];
    }

    @Override
    public byte[] generateExcelForLeaves() {
        //Check if user is manager
        if (!rolesModule.hasAdminRole()) {
            log.info("User does not have the required authorization to download leave data");
            throw new SecurityException("User does not have the required authorization to download leave data");
        }

        List<Leave> leaves = leaveRepository.findAll();

        // Create Excel file from the retrieved leaves
        if (!leaves.isEmpty()) {
            try {
                log.info("Excel generated for leave data");
                return excelModule.createExcelBytesForLeaves(leaves);
            } catch (IOException e) {
                log.error("Error creating Excel file for leave data: ", e);
            }
        }

        return new byte[0];
    }

    @Override
    public List<Leave> getApprovedLeaves() {
        return leaveRepository.getLeavesByApprovalStatus("Approved");
    }

    @Override
    public List<Leave> getRejectedLeaves() {
        return leaveRepository.getLeavesByApprovalStatus("Rejected");
    }

    @Override
    public Long getApprovedLeavesCount() {
        Long approvedLeavesCount = leaveRepository.countLeavesByIsApproved("Approved");
        log.info("Fetched number of total approved leaves. Total number of approved leaves: "+approvedLeavesCount);
        return approvedLeavesCount;
    }

    @Override
    public Long getRejectedLeavesCount() {
        Long rejectedLeavesCount = leaveRepository.countLeavesByIsApproved("Rejected");
        log.info("Fetched number of total rejected leaves. Total number of rejected leaves: "+rejectedLeavesCount);
        return rejectedLeavesCount;
    }

}
