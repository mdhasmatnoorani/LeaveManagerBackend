package com.hasmat.leaveManager.controller.impl;

import com.hasmat.leaveManager.model.Leave;
import com.hasmat.leaveManager.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@RestController
@RequestMapping(value = "/api/v1/notification")
@CrossOrigin
@Slf4j
public class NotificationControllerImpl {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/leave/approval")
    public ResponseEntity<String> notifyLeaveApproval(@RequestBody Leave leave) {
        try {
            notificationService.notifyLeaveApproval(leave);
            return ResponseEntity.ok("Leave approval notification sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error sending leave approval notification: " + e.getMessage());
        }
    }
}
