package com.hasmat.leaveManager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@Entity
@Table(name = "`users`")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_firstname")
    private String userFirstName;

    @Column(name = "user_lastname")
    private String userLastName;

    @Column(name = "user_department")
    private String userDepartment;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "emp_id")
    private String empId;

    @Column(name = "created_date")
    private LocalDateTime createdAt;

    @Column(name = "updated_date", nullable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

}
