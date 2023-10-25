package com.first.capstone.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CronExpressionDTO {
    private String seconds;
    private String minutes;
    private String hours;
    private String daysOfMonth;
    private String months;
    private String daysOfWeek;

    // Getters and setters
}
