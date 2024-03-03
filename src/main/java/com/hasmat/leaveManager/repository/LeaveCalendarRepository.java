package com.hasmat.leaveManager.repository;

import com.hasmat.leaveManager.model.LeaveCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveCalendarRepository extends JpaRepository<LeaveCalendar, Integer> {
}
