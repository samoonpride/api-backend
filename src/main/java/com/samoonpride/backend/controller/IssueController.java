package com.samoonpride.backend.controller;

import com.samoonpride.backend.dto.IssueBubbleDto;
import com.samoonpride.backend.dto.IssueDto;
import com.samoonpride.backend.dto.request.CreateIssueRequest;
import com.samoonpride.backend.dto.request.UpdateIssueStatusRequest;
import com.samoonpride.backend.enums.IssueStatus;
import com.samoonpride.backend.serviceImpl.IssueServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/issue")
public class IssueController {
    private final IssueServiceImpl issueService;

    @GetMapping("/get/all")
    @ResponseBody
    public ResponseEntity<List<IssueDto>> getAllIssues() {
        return ResponseEntity
                .ok()
                .body(issueService.getAllIssues());
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createIssue(@RequestBody CreateIssueRequest createIssueRequest) {
        issueService.createIssue(createIssueRequest);
    }

    // update status of issue
    @PatchMapping("/update/{issueId}/status")
    @ResponseStatus(HttpStatus.OK)
    public void updateIssue(@PathVariable int issueId, @RequestBody UpdateIssueStatusRequest updateIssueStatusRequest) {
        issueService.updateIssueStatus(issueId, updateIssueStatusRequest);
    }

    // reopen issue
    @PatchMapping("/reopen/{issueId}")
    @ResponseStatus(HttpStatus.OK)
    public void reopenIssue(@PathVariable int issueId) {
        issueService.reopenIssue(issueId);
    }

    // get latest 10 issues
    @GetMapping("/line-user/get/{userId}/latest")
    @ResponseBody
    public ResponseEntity<List<IssueBubbleDto>> getLatestIssues(
            @PathVariable String userId,
            @RequestParam(required = false, defaultValue = "IN_CONSIDERATION,IN_PROGRESS") List<IssueStatus> status
    ) {
        return ResponseEntity
                .ok()
                .body(issueService.getLatestTenIssuesByLineUserAndStatus(userId, status));
    }

    @GetMapping("/line-user/get/{userId}/all")
    @ResponseBody
    public ResponseEntity<List<IssueBubbleDto>> getAllIssues(
            @PathVariable String userId,
            @RequestParam(required = false, defaultValue = "IN_CONSIDERATION,IN_PROGRESS") List<IssueStatus> status
    ) {
        return ResponseEntity
                .ok()
                .body(issueService.getAllIssuesByLineUserAndStatus(userId, status));
    }
}
