package com.hasmat.leaveManager.exceptions;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
