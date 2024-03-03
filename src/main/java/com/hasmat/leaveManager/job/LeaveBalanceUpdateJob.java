package com.hasmat.leaveManager.job;

import com.hasmat.leaveManager.model.Leave;
import com.hasmat.leaveManager.model.LeaveBalance;
import com.hasmat.leaveManager.repository.LeaveBalanceRepository;
import com.hasmat.leaveManager.repository.LeaveRepository;
import com.hasmat.leaveManager.utility.Duration;
import com.hasmat.leaveManager.utility.Timer;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@Component
@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class LeaveBalanceUpdateJob implements Job {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Value("${employee.total-leaves}")
    private int totalEmployeeLeaves;

    public static final String LAST_EXECUTION_TIME_KEY = "lastExecutionTime";

    @Transactional
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("-- Checking for new leave entries");

        // Retrieve lastExecutionTime from JobDataMap
        LocalDateTime lastExecutionTime = (LocalDateTime) context.getJobDetail().getJobDataMap().get(LAST_EXECUTION_TIME_KEY);

        // Fetch all leave entries created or modified after the last execution time
        List<Leave> newLeaveEntries = leaveRepository.findNewLeaveEntriesByLastExecutionTime(lastExecutionTime);
        log.info("-- Number of new leave entries found: {}", newLeaveEntries.size());

        if (!newLeaveEntries.isEmpty()) {
            log.info("-- New leave entries found. Updating leave balance");

            // Update leave balances for each employee
            for (String empId : leaveRepository.findAllEmployeeIds()) {
                updateLeaveBalance(empId);
            }
            log.info("-- Leave balance updated.");
        } else {
            log.info("-- No new leave entries found. Skipping leave balance update");
        }
            context.getJobDetail().getJobDataMap().put(LAST_EXECUTION_TIME_KEY, LocalDateTime.now());
    }

    @Transactional
    private void updateLeaveBalance(String empId) {
        try {
            log.info("-- UpdateLeaveBalance Job execution started");
            Timer timer = new Timer();
            // Fetch all leave records for a specific employee
            List<Leave> employeeApprovedLeaves = leaveRepository.findByEmpIdAndIsApproved(empId, "Approved");

            // Calculate total days of leave taken
            int totalDaysTaken = 0;
            for (Leave leave : employeeApprovedLeaves) {
                if (leave.getIsProcessed() == 0) {
                    totalDaysTaken += calculateDaysBetween(leave.getLeaveFrom(), leave.getLeaveTill());
                    leave.setIsProcessed(1); // Mark the leave as processed
                    leaveRepository.save(leave);
                }
            }

            // Get the current leave balance if available
            List<LeaveBalance> leaveBalances = leaveBalanceRepository.findByEmpId(empId);

            if (leaveBalances.isEmpty()) {
                // If no record exists in leave_balance table, create one
                LeaveBalance newLeaveBalance = new LeaveBalance();
                newLeaveBalance.setEmpId(empId);
                newLeaveBalance.setBalanceLeave(0);
                leaveBalanceRepository.save(newLeaveBalance);
                leaveBalances = List.of(newLeaveBalance);
            }

            // Update the leave balance by considering all leave records
            LeaveBalance leaveBalance = leaveBalances.get(0);
            int updatedLeaveBalance = totalEmployeeLeaves - totalDaysTaken;

            // Ensure updatedLeaveBalance is non-negative
            updatedLeaveBalance = Math.max(0, updatedLeaveBalance);

            // Increment the leave balance in the leave_balance table
            LocalDateTime updatedAt = LocalDateTime.now();  // Get the current timestamp
            leaveBalanceRepository.updateLeaveBalance(empId, updatedLeaveBalance, updatedAt);

            // Reset the isProcessed flag to 0 for all processed leaves
            for (Leave leave : employeeApprovedLeaves) {
                if (leave.getIsProcessed() == 1) {
                    leave.setIsProcessed(0);
                    leaveRepository.save(leave);
                }
            }

            log.info("Leave balance updated for employee: {}", empId);
            log.info("-- Update LeaveBalance Job execution completed in "+ timer.getTime(Duration.Unit.SECONDS) + " seconds");
        } catch (Exception e) {
            log.error("Error updating leave balance for employee: {}", empId, e);
            // throw the exception here if you want the job to stop on errors
        }
    }


    private int calculateDaysBetween(LocalDateTime startDate, LocalDateTime endDate) {
        // Calculate the days between two LocalDateTime values
        return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1; // +1 to include both start and end dates
    }

}

