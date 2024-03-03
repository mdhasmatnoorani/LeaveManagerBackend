package com.hasmat.leaveManager.utility;

import java.util.UUID;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public class LeaveIdGenerator {

    private static final String LEAVE_ID_PREFIX = "LV";
    private static final int LEAVE_ID_LENGTH = 6;

    public static String generateLeaveId() {
        UUID uuid = UUID.randomUUID();
        String randomString = uuid.toString().replaceAll("-", "");

        randomString = randomString.substring(0, LEAVE_ID_LENGTH).toUpperCase();

        String leaveId = LEAVE_ID_PREFIX + randomString;;

        return leaveId;
    }
}
