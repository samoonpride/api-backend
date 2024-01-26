package com.samoonpride.backend.controller;

import com.samoonpride.backend.dto.request.CreateReportRequest;
import com.samoonpride.backend.dto.request.UpdateReportStatusRequest;
import com.samoonpride.backend.serviceImpl.ReportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final ReportServiceImpl reportService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReport(@RequestBody CreateReportRequest createReportRequest) {
        reportService.createReport(createReportRequest);
    }

    // update status of report
    @PostMapping("/update/status")
    @ResponseStatus(HttpStatus.OK)
    public void updateReport(@RequestBody UpdateReportStatusRequest updateReportStatusRequest) {
        reportService.updateReportStatus(updateReportStatusRequest);
    }
}
