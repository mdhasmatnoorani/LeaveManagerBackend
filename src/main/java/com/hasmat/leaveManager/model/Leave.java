package com.hasmat.leaveManager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="`leave`")
@Entity
public class Leave {

    @Id
    @Column(name="leave_id")
    private String leaveId;

    @Column(name="emp_id")
    private String empId;

    @Column(name="leave_type")
    private String leaveType;

    @Column(name="leave_category")
    private String leaveCategory;

    @Column(name="leave_from")
    private LocalDateTime leaveFrom;

    @Column(name="leave_till")
    private LocalDateTime leaveTill;

    @Column(name="leave_reason")
    private String leaveReason;

    @Column(name="approved_by")
    private String approvedBy;

    @Column(name="is_approved")
    private String isApproved;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="is_processed")
    private int isProcessed = 0;

    @Column(name="last_execution_time")
    private LocalDateTime lastExecutionTime;

    @PrePersist
    void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}
