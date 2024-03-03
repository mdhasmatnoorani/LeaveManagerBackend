package com.hasmat.leaveManager.utility;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public class Common {
    public static boolean hasValue(String string) {
        if(string == null || string.length() == 0) {
            return false;
        }
        return true;
    }

    public static boolean hasValue(Boolean value) {
        if(value !=  null) {
            return true;
        }
        return false;

    }
}
