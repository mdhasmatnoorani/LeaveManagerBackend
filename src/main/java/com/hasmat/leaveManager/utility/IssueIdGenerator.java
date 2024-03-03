package com.hasmat.leaveManager.utility;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Hasmat.Noorani
 * @since 11-02-23
 */
@Component
public class IssueIdGenerator {
    private static final String ISSUE_ID_PREFIX = "IS";
    private static final int ISSUE_ID_LENGTH = 7;

    public static String generateIssueId() {
        UUID uuid = UUID.randomUUID();
        String randomString = uuid.toString().replaceAll("-", "");

        randomString = randomString.substring(0, ISSUE_ID_LENGTH).toUpperCase();

        String issueId = ISSUE_ID_PREFIX + randomString;;

        return issueId;
    }
}
