package com.samoonpride.backend.service;

import com.samoonpride.backend.dto.request.CreateReportRequest;
import com.samoonpride.backend.model.Report;

public interface ReportService {
    Report createReport(CreateReportRequest createReportRequest);
}
