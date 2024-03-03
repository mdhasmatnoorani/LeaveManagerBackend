package com.hasmat.leaveManager.controller.impl;

import com.hasmat.leaveManager.controller.LeaveController;
import com.hasmat.leaveManager.model.Leave;
import com.hasmat.leaveManager.service.LeaveService;
import com.hasmat.leaveManager.validations.LeaveValidation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@RestController
@RequestMapping(value = "/api/v1/leave")
@CrossOrigin
@Slf4j
public class LeaveControllerImpl implements LeaveController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private LeaveValidation leaveValidation;

    @Override
    @PostMapping(value = "/add")
    public ResponseEntity<Leave> addLeave(@RequestBody Leave leave, @RequestParam String empId) {
        try {
            // Validate leave before creating
            ResponseEntity<Leave> validationResponse = leaveValidation.validateLeave(leave, empId);
            if (validationResponse != null) {
                return validationResponse;
            }

            Leave createdLeave = leaveService.createLeave(leave, empId);

            log.info("Leave added successfully with id: " + createdLeave.getLeaveId() + " for empId: " + empId);

            if (createdLeave != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(createdLeave);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            log.error("Error adding leave", e);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @PostMapping(value = "/approve")
    public ResponseEntity<Leave> approveLeave(@RequestParam String leaveId) {
        try {
            Leave approvedLeave = leaveService.approveLeave(leaveId);
            if (approvedLeave != null) {
                return ResponseEntity.ok(approvedLeave);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (IllegalArgumentException e) {
            log.error("Error occurred while approving leave: " +e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error occurred while approving leave: " +e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @PostMapping(value = "/reject")
    public ResponseEntity<Leave> rejectLeave(@RequestParam String leaveId) {
        try {
            Leave rejectedLeave = leaveService.rejectLeave(leaveId);
            if (rejectedLeave != null) {
                return ResponseEntity.ok(rejectedLeave);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (IllegalArgumentException e) {
            log.error("Error occurred while rejecting leave: " +e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error occurred while rejecting leave: " +e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @GetMapping(value = "/get-leave-status")
    public ResponseEntity<String> getLeaveStatus(@RequestParam String leaveId) {
        try{
           String leaveStatus = leaveService.getLeaveStatus(leaveId);
            return ResponseEntity.ok(leaveStatus);
        }catch(Exception ex) {
        throw ex;
        }
    }

    @Override
    @GetMapping(value = "/{empId}")
    public ResponseEntity<List<Leave>> getLeavesByEmpId(@PathVariable String empId) {
        try {
            List<Leave> leaves = leaveService.getLeaveByEmpId(empId);
            if (!leaves.isEmpty()) {
                return ResponseEntity.ok(leaves);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            log.error("Error getting leaves for empId: " + empId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @GetMapping(value = "/all")
    public ResponseEntity<List<Leave>> getAllLeaves() {
        try {
            List<Leave> leaves = leaveService.getAllLeaves();
            if (!leaves.isEmpty()) {
                return ResponseEntity.ok(leaves);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            log.error("Error getting all leaves ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @GetMapping(value = "/total-leaves")
    public ResponseEntity<?> getAllLeavesCount() {
        try {
            Long allLeavesCount = leaveService.getAllLeavesCount();
            return ResponseEntity.status(HttpStatus.OK).body(allLeavesCount);
        } catch (Exception e) {
            log.error("Failed to fetch total number of leaves ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch total number of leaves " + e.getMessage());
        }
    }

    //endpoint for generating Excel file
    @GetMapping(value = "/excel/{empId}")
    @Override
    public ResponseEntity<byte[]> generateExcelForEmpId(@PathVariable String empId, HttpServletResponse response) {
        try {
            byte[] excelBytes = leaveService.generateExcelForEmpId(empId);

            if (excelBytes.length > 0) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", "leave_data_" + empId + ".xlsx");
                log.info("Excel generated successfully");
                return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            log.error("Error generating Excel file for empId: " + empId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @GetMapping(value = "/download-excel")
    public ResponseEntity<byte[]> generateExcelForLeaves(HttpServletResponse response) {
        try {
            byte[] excelBytes = leaveService.generateExcelForLeaves();

            if (excelBytes.length > 0) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", "leave_data.xlsx");
                log.info("Excel generated successfully");
                return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            log.error("Error generating Excel file for leave data: " , e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @GetMapping(value = "/approved")
    public ResponseEntity<List<Leave>> getApprovedLeaves() {
        try {
            List<Leave> approvedLeaves = leaveService.getApprovedLeaves();

            if (!approvedLeaves.isEmpty()) {
                log.info("Fetched list of approved leaves");
                return ResponseEntity.ok(approvedLeaves);
            } else {
                log.info("No approved leaves found");
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            log.error("Error getting approved leaves", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @Override
    @GetMapping(value = "/rejected")
    public ResponseEntity<List<Leave>> getRejectedLeaves() {
        try {
            List<Leave> rejectedLeaves = leaveService.getRejectedLeaves();

            if (!rejectedLeaves.isEmpty()) {
                log.info("Fetched list of rejected leaves");
                return ResponseEntity.ok(rejectedLeaves);
            } else {
                log.info("No rejected leaves found");
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            log.error("Error getting rejected leaves", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @GetMapping("/total-approved")
    public ResponseEntity<?> getApprovedLeavesCount() {
        try {
            Long approvedLeavesCount = leaveService.getApprovedLeavesCount();
            return ResponseEntity.status(HttpStatus.OK).body(approvedLeavesCount);
        } catch (Exception e) {
            log.error("Failed to fetch total number approved leaves ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch total number approved leaves " + e.getMessage());
        }
    }

    @Override
    @GetMapping("/total-rejected")
    public ResponseEntity<?> getRejectedLeavesCount() {
        try {
            Long rejectedLeavesCount = leaveService.getRejectedLeavesCount();
            return ResponseEntity.status(HttpStatus.OK).body(rejectedLeavesCount);
        } catch (Exception e) {
            log.error("Failed to fetch total number of rejected leaves ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch total number of rejected leaves " + e.getMessage());
        }
    }

}

