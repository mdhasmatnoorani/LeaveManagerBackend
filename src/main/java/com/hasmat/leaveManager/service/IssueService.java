package com.hasmat.leaveManager.service;

import com.hasmat.leaveManager.model.Issue;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    Issue createIssue(Issue issue);
    List<Issue> getAllIssues();
    Optional<Issue> getIssueById(String id);
    Long countTotalIssues();
    byte[] generateExcelForAllIssues();
}
