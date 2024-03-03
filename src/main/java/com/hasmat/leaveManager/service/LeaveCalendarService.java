package com.hasmat.leaveManager.service;

import com.hasmat.leaveManager.model.LeaveCalendar;

import java.util.List;
import java.util.Optional;

public interface LeaveCalendarService {
    LeaveCalendar addLeaveCalendar(LeaveCalendar leaveCalendar);
    Optional<LeaveCalendar> getLeaveCalendarById(Integer id);
    List<LeaveCalendar> getLeaveCalendar();
}
