package com.first.capstone.dto;

import lombok.Data;

@Data
public class LicenseCountDTO {
    private long totalLicenses;
    private long licensesLessThanZero;
    private long licensesGreaterThan45;
    private long licensesLessThan45;
    
    // Constructors, getters, and setters
}
