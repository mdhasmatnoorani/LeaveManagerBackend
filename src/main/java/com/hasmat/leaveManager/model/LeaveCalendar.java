package com.hasmat.leaveManager.model;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="`leave_calendar`")
@Entity
public class LeaveCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="date")
    private LocalDate date;

    @Column(name="leave_type")
    private String leaveType;

    @Column(name="description")
    private String description;

    @Column(name="added_by")
    private String added_by;

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
