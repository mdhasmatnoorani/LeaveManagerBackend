package com.hasmat.leaveManager.controller.impl;

import com.hasmat.leaveManager.model.User;
import com.hasmat.leaveManager.response.AuthResponse;
import com.hasmat.leaveManager.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@RestController
@RequestMapping(value="api/v1/user")
@CrossOrigin
public class AuthControllerImpl {

    @Autowired
    AuthService authService;

    @PostMapping(path= "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(operationId="Auth__AuthenticateUser", summary = "Authenticate User", description  =  "Authenticate User For Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Response on Success", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "4XX/5XX", description = "Response on Failure", content = {@Content(mediaType = "application/json")}) })
    public AuthResponse authenticateUser(@RequestBody User user) {
        return authService.authenticateUser(user);
    }
}
