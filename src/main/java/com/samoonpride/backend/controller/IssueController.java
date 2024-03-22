package com.samoonpride.backend.controller;

import com.samoonpride.backend.authentication.JwtUtil;
import com.samoonpride.backend.config.ModelMapperConfig;
import com.samoonpride.backend.dto.IssueBubbleDto;
import com.samoonpride.backend.dto.IssueDto;
import com.samoonpride.backend.dto.request.CreateIssueRequest;
import com.samoonpride.backend.dto.request.UpdateIssueRequest;
import com.samoonpride.backend.dto.request.UpdateIssueStatusRequest;
import com.samoonpride.backend.enums.IssueStatus;
import com.samoonpride.backend.serviceImpl.IssueServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/issue")
public class IssueController {
    private final IssueServiceImpl issueService;
    private final JwtUtil jwtUtil;
    private final ModelMapperConfig modelMapperConfig;

    @GetMapping("/get/all")
    @ResponseBody
    public ResponseEntity<List<IssueDto>> getAllIssuesByUser() {
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
    public void reopenIssue(HttpServletRequest request, @PathVariable int issueId) {
        Claims claims = jwtUtil.resolveClaims(request);
        issueService.reopenIssue(issueId, claims);
    }

    // update issue
    @PatchMapping("/{issueId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateIssue(HttpServletRequest request,
                            @PathVariable int issueId,
                            @RequestParam(value = "image", required = false) MultipartFile media,
                            @RequestParam("issue") JSONObject issueJson
    ) {
        Claims claims = jwtUtil.resolveClaims(request);
        UpdateIssueRequest updateIssueRequest = modelMapperConfig.modelMapper().map(issueJson, UpdateIssueRequest.class);
        issueService.updateIssue(issueId, updateIssueRequest, media, claims);
    }

    // get latest 10 issues
    @GetMapping("/line-user/get/{userId}/latest")
    @ResponseBody
    public ResponseEntity<List<IssueBubbleDto>> getLatestIssuesByUser(
            @PathVariable String userId,
            @RequestParam(required = false, defaultValue = "IN_CONSIDERATION,IN_PROGRESS") List<IssueStatus> status
    ) {
        return ResponseEntity
                .ok()
                .body(issueService.getLatestTenIssuesByLineUserAndStatus(userId, status));
    }

    @GetMapping("/line-user/get/{userId}/all")
    @ResponseBody
    public ResponseEntity<List<IssueBubbleDto>> getAllIssuesByUser(
            @PathVariable String userId,
            @RequestParam(required = false, defaultValue = "IN_CONSIDERATION,IN_PROGRESS") List<IssueStatus> status
    ) {
        return ResponseEntity
                .ok()
                .body(issueService.getAllIssuesByLineUserAndStatus(userId, status));
    }

    @GetMapping("/line-user/get/{userId}/distinct")
    @ResponseBody
    public ResponseEntity<List<IssueBubbleDto>> getDistinctIssuesByUser(
            @PathVariable String userId,
            @RequestParam(required = false, defaultValue = "IN_CONSIDERATION,IN_PROGRESS") List<IssueStatus> status
    ) {
        return ResponseEntity
                .ok()
                .body(issueService.getDistinctIssuesByLineUserAndStatus(userId, status));
    }

    @GetMapping("/line-user/get/{userId}/subscribed")
    @ResponseBody
    public ResponseEntity<List<IssueBubbleDto>> getSubscribedIssuesByUser(
            @PathVariable String userId,
            @RequestParam(required = false, defaultValue = "IN_CONSIDERATION,IN_PROGRESS") List<IssueStatus> status
    ) {
        return ResponseEntity
                .ok()
                .body(issueService.getSubscribedIssuesByLineUserAndStatus(userId, status));
    }
}
