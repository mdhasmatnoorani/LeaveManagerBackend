package com.hasmat.leaveManager.repository;

import com.hasmat.leaveManager.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@Repository
public interface LeaveRepository extends JpaRepository<Leave, String> {
    Optional<Leave> findByLeaveIdAndEmpId(String leaveId, String empId);

    List<Leave> findByEmpId(String empId);

    List<Leave> findByLeaveId(String leaveId);

    List<Leave> findByEmpIdAndLeaveId(String empId, String leaveId);

    List<Leave> findByEmpIdAndIsApproved(String empId, String isApproved);

    @Query("SELECT COUNT(l) > 0 FROM Leave l WHERE l.leaveFrom <= :leaveToDate AND l.leaveTill >= :leaveFromDate")
    boolean existsByLeaveFromBetween(
            @Param("leaveFromDate") LocalDateTime leaveFromDate,
            @Param("leaveToDate") LocalDateTime leaveToDate
    );

    @Query("SELECT l FROM Leave l WHERE l.lastExecutionTime > :lastExecutionTime")
    //@EntityGraph(attributePaths = "employee")
    List<Leave> findNewLeaveEntriesByLastExecutionTime(@Param("lastExecutionTime") LocalDateTime lastExecutionTime);

    @Query("SELECT DISTINCT l.empId FROM Leave l")
    List<String> findAllEmployeeIds();

    @Query("SELECT l FROM Leave l WHERE l.isApproved = :isApproved")
    List<Leave> getLeavesByApprovalStatus(@Param("isApproved") String isApproved);

    Long countLeavesByIsApproved(String isApproved);

}
