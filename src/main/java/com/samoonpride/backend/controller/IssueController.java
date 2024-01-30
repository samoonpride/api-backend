package com.samoonpride.backend.controller;

import com.samoonpride.backend.dto.IssueDto;
import com.samoonpride.backend.dto.request.CreateIssueRequest;
import com.samoonpride.backend.dto.request.GetIssueByLineUserRequest;
import com.samoonpride.backend.dto.request.UpdateIssueStatusRequest;
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

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createIssue(@RequestBody CreateIssueRequest createIssueRequest) {
        issueService.createIssue(createIssueRequest);
    }

    // update status of issue
    @PostMapping("/update/status")
    @ResponseStatus(HttpStatus.OK)
    public void updateIssue(@RequestBody UpdateIssueStatusRequest updateIssueStatusRequest) {
        issueService.updateIssueStatus(updateIssueStatusRequest);
    }

    // get latest 10 issues
    @GetMapping("/line-user/get/latest")
    @ResponseBody
    public ResponseEntity<List<IssueDto>> getLatestIssues(@RequestBody GetIssueByLineUserRequest getIssueByLineUserRequest) {
        return ResponseEntity.ok().body(
                issueService.getLatestTenIssuesByLineUserAndStatus(
                        getIssueByLineUserRequest.getUserId(),
                        getIssueByLineUserRequest.getStatus()
                )
        );





    }

    @GetMapping("/line-user/get/all")
    @ResponseBody
    public ResponseEntity<List<IssueDto>> getAllIssues(@RequestBody GetIssueByLineUserRequest getIssueByLineUserRequest) {
        return ResponseEntity.ok().body(
                issueService.getAllIssuesByLineUserAndStatus(
                        getIssueByLineUserRequest.getUserId(),
                        getIssueByLineUserRequest.getStatus()
                )
        );
    }
}
