package com.samoonpride.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActivityLogAction {
    // staff
    STAFF_LOGIN("STAFF_LOGIN"),
    STAFF_REGISTER("STAFF_REGISTER"),
    STAFF_APPROVAL("STAFF_APPROVAL"),
    STAFF_DECLINATION("STAFF_DECLINATION"),
    STAFF_CHANGE_PASSWORD("STAFF_CHANGE_PASSWORD"),
    STAFF_DELETED("STAFF_DELETED"),
    STAFF_UPDATE_ISSUE("STAFF_UPDATE_ISSUE"),
    STAFF_REOPEN_ISSUE("STAFF_REOPEN_ISSUE"),
    // User-related actions
    USER_CREATED("USER_CREATED"),
    USER_CREATE_ISSUE("USER_CREATE_ISSUE"),
    USER_SUBSCRIBE_ISSUE("USER_SUBSCRIBE_ISSUE"),
    USER_UNSUBSCRIBE_ISSUE("USER_UNSUBSCRIBE_ISSUE"),
    // Issue-related actions
    ISSUE_NOTIFICATION("ISSUE_NOTIFICATION");



    private final String action;
}
