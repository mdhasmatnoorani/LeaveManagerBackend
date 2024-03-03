package com.hasmat.leaveManager.service.impl;

import com.hasmat.leaveManager.constants.LeaveCategory;
import com.hasmat.leaveManager.model.LeaveCalendar;
import com.hasmat.leaveManager.repository.LeaveCalendarRepository;
import com.hasmat.leaveManager.service.LeaveCalendarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LeaveCalendarServiceImp implements LeaveCalendarService {

    @Autowired
    LeaveCalendarRepository leaveCalendarRepository;


    @Override
    public LeaveCalendar addLeaveCalendar(LeaveCalendar leaveCalendar) {
        leaveCalendar.setLeaveType(LeaveCategory.NATIONAL_HOLIDAY);
        log.info("Leave calendar added for: "+leaveCalendar.getLeaveType());
        return leaveCalendarRepository.save(leaveCalendar);
    }

    @Override
    public Optional<LeaveCalendar> getLeaveCalendarById(Integer id) {
        log.info("Fetched leave from leave calendar with id: "+id);
        return leaveCalendarRepository.findById(id);
    }

    @Override
    public List<LeaveCalendar> getLeaveCalendar() {
        log.info("Fetched all leaves from leave calendar");
        return leaveCalendarRepository.findAll();
    }
}
