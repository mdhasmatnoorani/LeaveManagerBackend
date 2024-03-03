package com.hasmat.leaveManager.exceptions;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

