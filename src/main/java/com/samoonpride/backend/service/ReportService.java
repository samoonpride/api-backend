package com.samoonpride.backend.service;

import com.samoonpride.backend.dto.request.CreateReportRequest;
import com.samoonpride.backend.dto.request.UpdateReportStatusRequest;
import com.samoonpride.backend.model.Report;

public interface ReportService {
    Report createReport(CreateReportRequest createReportRequest);
    void updateReportStatus(UpdateReportStatusRequest updateReportStatusRequest);
}
