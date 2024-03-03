package com.hasmat.leaveManager.utility;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public class StringConverter {

    public static String removeFormattingCharacters(String input) {
        String ret = null;
        if (Common.hasValue(input)) {
            ret = input.replaceAll("\r|\n|\\s|\t", "");
        }
        return ret;
    }
}
