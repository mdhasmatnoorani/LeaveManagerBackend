package com.hasmat.leaveManager.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class APIError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    // Complete HttpStatus object
    @JsonIgnore
    private HttpStatus httpStatus;

    // Customer error message to the client API
    private Integer status;

    // Customer error message to the client API
    private String message;

    // Reason of exception to the client API
    private String reason;

    // Detailed url for error
    @JsonIgnore
    private String path;

    // The error message associated with the domain specific
    private String errorCode;

    // Holds an array of sub-errors that happened
    private List<String> errorDescriptions;
}
