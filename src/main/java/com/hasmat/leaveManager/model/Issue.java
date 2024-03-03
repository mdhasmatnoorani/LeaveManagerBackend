package com.hasmat.leaveManager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author Hasmat.Noorani
 * @since 10-02-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="`issues`")
@Entity
public class Issue {

    @Id
    @Column(name="issue_id")
    private String issueId;

    @Column(name="issue_title")
    private String issueTitle;

    @Column(name="issue_description")
    private String issueDescription;

    @Column(name="concerned_authority")
    private String concernedAuthority;

    @Column(name="reported_by")
    private String reportedBy;

    @Column(name="status")
    private String status;

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
