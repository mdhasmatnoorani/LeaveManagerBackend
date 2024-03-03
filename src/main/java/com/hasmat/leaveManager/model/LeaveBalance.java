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
@Table(name="leave_balance")
@Entity
public class LeaveBalance {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="balance_leave")
    private int balanceLeave;

    @Column(name="emp_id")
    private String empId;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void setCreatedAt(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}