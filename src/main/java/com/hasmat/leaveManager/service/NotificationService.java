package com.hasmat.leaveManager.service;

import com.hasmat.leaveManager.model.Leave;

public interface NotificationService {
    void notifyLeaveApproval(Leave leave);
    void notifyLeaveRejection(Leave leave);
}
