package com.hasmat.leaveManager.controller;

import com.hasmat.leaveManager.model.Leave;
import org.springframework.http.ResponseEntity;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public interface NotificationController {
    ResponseEntity<String> notifyLeaveApproval(Leave leave);
}
