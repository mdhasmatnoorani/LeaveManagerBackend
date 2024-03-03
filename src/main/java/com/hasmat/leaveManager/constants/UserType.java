package com.hasmat.leaveManager.constants;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@Data
@NoArgsConstructor
@ToString
public class UserType {
    public static final String MANAGER = "Manager";
    public static final String EMPLOYEE = "Employee";
    public static final String ADMIN = "Admin";
    public static final String SUPER_ADMIN = "Super Admin";


}
