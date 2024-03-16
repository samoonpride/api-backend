package com.samoonpride.backend.utils;

import com.samoonpride.backend.dto.request.UpdateIssueRequest;
import com.samoonpride.backend.model.Issue;
import com.samoonpride.backend.model.LineUser;
import com.samoonpride.backend.model.Staff;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LogMessageFormatter {
    public static String formatStaffLogin(Staff staff) {
        return String.format(
                "Staff: %s logged in to the system",
                staff.getUsername()
        );
    }

    public static String formatStaffRegister(Staff staff) {
        return String.format(
                "New staff: %s registered",
                staff.getUsername()
        );
    }

    public static String formatStaffApproval(String approvingStaff, Staff staff) {
        return String.format(
                "%s approved %s as %s",
                approvingStaff,
                staff.getUsername(),
                staff.getRole()
        );
    }

    public static String formatStaffDeclination(String username, Staff staff) {
        return String.format(
                "%s declined %s",
                username,
                staff.getUsername()
        );
    }

    public static String formatStaffChangePassword(Staff staff) {
        return String.format(
                "Staff: %s changed password",
                staff.getUsername()
        );
    }

    public static String formatStaffDeleted(String staffWhoDeleted, Staff staff) {
        return String.format(
                "Staff: %s deleted %s",
                staffWhoDeleted,
                staff.getUsername()
        );
    }

    public static String formatStaffUpdateIssue(String username, Issue issue, UpdateIssueRequest updateIssueRequest) {
        return String.format(
                "Staff: %s updated issue (%d): \n%s \nto: \n%s",
                username,
                issue.getId(),
                issue,
                updateIssueRequest
        );
    }

    public static String formatStaffReopenIssue(String username, Issue issue) {
        return String.format(
                "Staff: %s reopened issue (%d) %s",
                username,
                issue.getId(),
                issue.getTitle()
        );
    }

    public static String formatUserCreated(LineUser lineUser) {
        return String.format(
                "User: %s created",
                lineUser.getDisplayName()
        );
    }

    public static String formatUserCreateIssue(String lineUserId, Issue issue) {
        return String.format(
                "User %s created issue (%d) %s",
                lineUserId,
                issue.getId(),
                issue.getTitle()
        );
    }

    public static String formatUserSubscribeIssue(String lineUserId, Issue issue) {
        return String.format(
                "User %s subscribed issue (%d) %s",
                lineUserId,
                issue.getId(),
                issue.getTitle()
        );
    }

    public static String formatUserUnsubscribeIssue(String lineUserId, Issue issue) {
        return String.format(
                "User %s unsubscribed issue (%d) %s",
                lineUserId,
                issue.getId(),
                issue.getTitle()
        );
    }

    public static String formatIssueNotification(Issue issue) {
        return String.format(
                "Notification for issue (%d) %s",
                issue.getId(),
                issue.getTitle()
        );
    }

}