package com.hasmat.leaveManager.controller.impl;

import com.hasmat.leaveManager.controller.LeaveCalendarController;
import com.hasmat.leaveManager.model.LeaveCalendar;
import com.hasmat.leaveManager.service.LeaveCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1/leave-calendar")
@CrossOrigin
public class LeaveCalendarControllerImpl implements LeaveCalendarController {

    @Autowired
    private LeaveCalendarService leaveCalendarService;

    @PostMapping("/add")
    @Override
    public ResponseEntity<LeaveCalendar> addLeaveCalendar(@RequestBody LeaveCalendar leaveCalendar) {
        try {
            LeaveCalendar addedLeaveCalendar = leaveCalendarService.addLeaveCalendar(leaveCalendar);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedLeaveCalendar);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<LeaveCalendar> getLeaveCalendarById(@PathVariable Integer id) {
        try {
            Optional<LeaveCalendar> leaveCalendar = leaveCalendarService.getLeaveCalendarById(id);
            return leaveCalendar.map(value -> ResponseEntity.ok().body(value))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all")
    @Override
    public ResponseEntity<List<LeaveCalendar>> getLeaveCalendar() {
        try {
            List<LeaveCalendar> leaveCalendarList = leaveCalendarService.getLeaveCalendar();
            return ResponseEntity.ok().body(leaveCalendarList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
