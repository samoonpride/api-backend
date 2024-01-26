package com.samoonpride.backend.dto.request;

import com.samoonpride.backend.enums.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateReportStatusRequest {
    private int reportId;
    private ReportStatus status;
}
