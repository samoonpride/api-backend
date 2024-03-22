package com.samoonpride.backend.controller;

import com.samoonpride.backend.dto.LogDto;
import com.samoonpride.backend.serviceImpl.ActivityLogServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/log")
public class LogController {
    private final ActivityLogServiceImpl activityLogService;

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<LogDto>> getAllLogs() {
        return ResponseEntity
                .ok()
                .body(activityLogService.getAllLogs());
    }
}
