package com.hasmat.leaveManager.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Hasmat.Noorani
 * @since 12-02-24
 */
public class FormatDateTime {

    public static String formatDateAndTime(LocalDateTime localDateTime, String pattern) {
        if (localDateTime == null) {
            throw new IllegalArgumentException("LocalDateTime cannot be null");
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }
}

