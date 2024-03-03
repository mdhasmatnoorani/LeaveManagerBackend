package com.hasmat.leaveManager.controller;

import com.hasmat.leaveManager.model.Issue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
public interface IssueController {
    ResponseEntity<?> createIssue(Issue issue);
    ResponseEntity<?> getAllIssues();
    ResponseEntity<?> getIssueById(String id);
    ResponseEntity<?> getTotalIssueCount();
    ResponseEntity<byte[]> generateExcelForAllIssues(HttpServletResponse response);
}
