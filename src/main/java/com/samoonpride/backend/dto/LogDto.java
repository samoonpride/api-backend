package com.samoonpride.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LogDto {
    private String createdDate;
    private String username;
    private String action;
    private String message;
}
