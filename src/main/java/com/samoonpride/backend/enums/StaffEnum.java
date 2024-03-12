package com.samoonpride.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StaffEnum {
    SUPER_OPERATOR(0),
    OPERATOR(1),
    STAFF(2),
    PENDING(3);

    private final int priority;

    // function to check if the role has higher priority than the given role
    // when the priority is lower, it means the role is higher
    public boolean hasLowerPriorityThan(StaffEnum role) {
        return this.priority >= role.priority;
    }

    public static StaffEnum fromString(String role) {
        return switch (role) {
            case "SUPER_OPERATOR" -> SUPER_OPERATOR;
            case "OPERATOR" -> OPERATOR;
            case "STAFF" -> STAFF;
            case "PENDING" -> PENDING;
            default -> throw new IllegalStateException("Unexpected value");
        };
    }


}
