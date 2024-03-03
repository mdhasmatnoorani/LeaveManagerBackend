package com.hasmat.leaveManager.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class APIErrorResponse {

    public static final String API_ERROR = "error";

    @Schema(description = "Error Object")
    @JsonProperty(API_ERROR)
    protected APIError apiError;

}
