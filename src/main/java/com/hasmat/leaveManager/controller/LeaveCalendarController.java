package com.hasmat.leaveManager.controller;

import com.hasmat.leaveManager.model.Leave;
import com.hasmat.leaveManager.model.LeaveCalendar;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public interface LeaveCalendarController {
    ResponseEntity<LeaveCalendar> addLeaveCalendar(LeaveCalendar leaveCalendar);
    ResponseEntity<LeaveCalendar> getLeaveCalendarById(Integer id);
    ResponseEntity<List<LeaveCalendar>> getLeaveCalendar();

}
