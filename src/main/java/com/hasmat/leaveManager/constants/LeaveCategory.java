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
public class LeaveCategory {
    public static final String CASUAL = "Casual";
    public static final String SICK = "Sick";
    public final static String MATERNITY = "Maternity";
    public final static String PATERNITY = "Paternity";
    public final static String UNPAID = "Unpaid";
    public final static String NATIONAL_HOLIDAY = "National Holiday";
    public final static String FESTIVE = "Festive";
    public final static String EARNED = "Earned";
}
