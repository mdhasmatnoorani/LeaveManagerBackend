package com.hasmat.leaveManager.repository;

import com.hasmat.leaveManager.model.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Integer> {

    List<LeaveBalance> findByEmpId(String empId);

    @Modifying
    @Query("UPDATE LeaveBalance SET balanceLeave = :remainingLeaves, updatedAt = :updatedAt WHERE empId = :empId")
    void updateLeaveBalance(
            @Param("empId") String empId,
            @Param("remainingLeaves") int remainingLeaves,
            @Param("updatedAt") LocalDateTime updatedAt
    );

    LeaveBalance findLeaveBalanceByEmpId(String empId);
}
