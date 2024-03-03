package com.hasmat.leaveManager.controller.impl;

import com.hasmat.leaveManager.controller.IssueController;
import com.hasmat.leaveManager.model.Issue;
import com.hasmat.leaveManager.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Hasmat.Noorani
 * @since 11-02-24
 */
// IssueControllerImpl.java

@RestController
@RequestMapping(value = "api/v1/issue")
@CrossOrigin
@Slf4j
public class IssueControllerImpl implements IssueController {

    @Autowired
    private IssueService issueService;

    @PostMapping(path= "/report", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(operationId="Create__NewIssue", summary = "Report Issue", description  =  "Creates issue object for reporting to concerned authority")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Issue reported successfully", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(mediaType = "application/json")}) })
    public ResponseEntity<?> createIssue(@RequestBody Issue issue) {
        try {
            Issue reportedIssue = issueService.createIssue(issue);
            return ResponseEntity.status(HttpStatus.CREATED).body(reportedIssue);
        } catch (Exception e) {
            log.error("Failed to report issue: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to report issue.");
        }
    }

    @GetMapping(path= "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(operationId="GetAll__Issues", summary = "Fetch All Issues", description  =  "Fetches all issue details reported")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(mediaType = "application/json")}) })
    public ResponseEntity<?> getAllIssues() {
        try {
            List<Issue> allIssues = issueService.getAllIssues();
            if(allIssues.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No issue found.");
            }else {
                return ResponseEntity.status(HttpStatus.OK).body(allIssues);
            }
        } catch (Exception e) {
            log.error("Failed to fetch all issue details: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch all issue details.");
        }
    }

    @GetMapping(path= "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(operationId="GetById__Issue", summary = "Fetch Issue By Id", description  =  "Fetches issues by issue id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(mediaType = "application/json")}) })
    public ResponseEntity<?> getIssueById(@PathVariable String id) {
        try {
            Optional<Issue> issue = issueService.getIssueById(id);
            if (issue.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(issue.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Issue not found.");
            }
        } catch (Exception e) {
            log.error("Failed to fetch issue details for id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch issue details.");
        }
    }


    @Override
    @GetMapping(path= "/total", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(operationId="GetTotal__Issues", summary = "Fetch total count issues", description  =  "Fetches number of total issues reported")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(mediaType = "application/json")}) })
    public ResponseEntity<?> getTotalIssueCount() {
        try {
            Long totaIssuesCount = issueService.countTotalIssues();
            if (totaIssuesCount == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No issues found.");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(totaIssuesCount);
            }
        } catch (Exception e) {
            log.error("Failed to count of total issues", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch total count of issues");
        }
    }

    @Override
    @GetMapping(path= "/download-excel", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(operationId="Download__Issues", summary = "Download Issues Data", description  =  "Downloads all issues data in excel format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE) }),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<byte[]> generateExcelForAllIssues(HttpServletResponse response) {
        try {
            byte[] excelBytes = issueService.generateExcelForAllIssues();

            if (excelBytes.length > 0) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", "issue_data.xlsx");
                log.info("Excel generated successfully for all issues");
                return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            log.error("Error generating Excel file for all issues data: " , e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
