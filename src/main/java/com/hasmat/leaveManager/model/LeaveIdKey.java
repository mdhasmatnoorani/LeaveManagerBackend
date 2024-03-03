package com.hasmat.leaveManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveIdKey implements Serializable {
    private String leaveId;
    private String empId;
}
