package com.samoonpride.backend.enums;

public enum ReportStatus {
    // รอพิจารณา
    IN_CONSIDERATION,
    // กำลังดำเนินการ
    IN_PROGRESS,
    // ระงับการดำเนินการ
    SUSPENDED,
    // ซ้ำซ้อน
    DUPLICATED,
    // ดำเนินการเสร็จสิ้น
    COMPLETED,
    // สแปม
    SPAM
}

//In Consideration
//In Progress
//Completed; Fixed
//Completed; Suspend - as not planned
//Completed; Duplicated - repeating issue
//Completed; Spam