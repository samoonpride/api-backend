package com.samoonpride.backend.serviceImpl;

import com.samoonpride.backend.dto.UserDto;
import com.samoonpride.backend.dto.request.CreateReportRequest;
import com.samoonpride.backend.dto.request.UpdateReportStatusRequest;
import com.samoonpride.backend.enums.UserEnum;
import com.samoonpride.backend.model.Report;
import com.samoonpride.backend.repository.ReportRepository;
import com.samoonpride.backend.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final LineUserServiceImpl lineUserService;
    private final MediaServiceImpl multimediaService;
    private final StaffServiceImpl staffService;
    private final ModelMapper modelMapper;

    @Override
    public Report createReport(CreateReportRequest createReportRequest) {
        Report report = new Report();
        // set user
        this.setReportUser(report, createReportRequest.getUser());
        // set another field
        report.setTitle(createReportRequest.getTitle());
        report.setLatitude(createReportRequest.getLatitude());
        report.setLongitude(createReportRequest.getLongitude());
        reportRepository.save(report);
        // set media
        multimediaService.createMultimedia(report, createReportRequest.getMedia());
        return report;
    }

    private void setReportUser(Report report, UserDto userDto) {
        if (userDto.getType() == UserEnum.LINE) {
            report.setLineUser(lineUserService.findByUserId(userDto.getKey()));
        } else {
            report.setStaff(staffService.getStaffByEmail(userDto.getKey()));
        }
    }

    public void updateReportStatus(UpdateReportStatusRequest updateReportStatusRequest) {
        Report report = reportRepository.findById(updateReportStatusRequest.getReportId());
        report.setStatus(updateReportStatusRequest.getStatus());
        reportRepository.save(report);
    }
}
