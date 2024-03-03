package com.hasmat.leaveManager.constants;

import lombok.*;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@Data
@NoArgsConstructor
@ToString
public class LeaveStatus {
    public static final String APPROVED = "Approved";
    public static final String PENDING = "Pending";
    public static final String REJECTED = "Rejected";
}
