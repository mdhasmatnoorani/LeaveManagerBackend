package com.hasmat.leaveManager.service.impl;

import com.hasmat.leaveManager.constants.IssueStatus;
import com.hasmat.leaveManager.model.Issue;
import com.hasmat.leaveManager.modules.ExcelModule;
import com.hasmat.leaveManager.repository.IssueRepository;
import com.hasmat.leaveManager.service.IssueService;
import com.hasmat.leaveManager.utility.IssueIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    ExcelModule excelModule;

    @Override
    public Issue createIssue(Issue issue) {
        issue.setIssueId(IssueIdGenerator.generateIssueId());
        issue.setStatus(IssueStatus.PENDING);
        log.info("Issue created successfully");
        return issueRepository.save(issue);
    }

    @Override
    public List<Issue> getAllIssues() {
        log.info("Fetched all issues");
        return issueRepository.findAll();
    }

    @Override
    public Optional<Issue> getIssueById(String id) {
        log.info("Fetched issue with id: " +id);
        return issueRepository.findById(id);
    }

    @Override
    public Long countTotalIssues() {
        Long totalIssueCount = issueRepository.count() ;
        log.info("Fetched all issues. No. of total issues: "+totalIssueCount);
        return totalIssueCount;
    }

    @Override
    public byte[] generateExcelForAllIssues() {
        List<Issue> issues = issueRepository.findAll();

        // Create Excel file from the retrieved users
        if (!issues.isEmpty()) {
            try {
                log.info("Excel generated for issues data");
                return excelModule.createExcelBytesForIssues(issues);
            } catch (IOException e) {
                log.error("Error creating Excel file for issues data: ", e);
            }
        }

        return new byte[0];
    }
}
