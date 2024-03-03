package com.hasmat.leaveManager.controller;

import com.hasmat.leaveManager.model.User;
import com.hasmat.leaveManager.response.AuthResponse;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public interface AuthController {
    AuthResponse authenticateUser(User user );
}
