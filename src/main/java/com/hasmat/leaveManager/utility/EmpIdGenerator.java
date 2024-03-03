package com.hasmat.leaveManager.utility;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@Component
public class EmpIdGenerator {

    private static final String EMP_ID_PREFIX = "EM";
    private static final int EMP_ID_LENGTH = 5;

    public static String generateEmpId() {
            UUID uuid = UUID.randomUUID();
            String randomString = uuid.toString().replaceAll("-", "");

            randomString = randomString.substring(0, EMP_ID_LENGTH).toUpperCase();

            String empId = EMP_ID_PREFIX + randomString;;

            return empId;
    }




}
