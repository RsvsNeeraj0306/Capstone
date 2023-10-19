package com.first.capstone.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LicenseCountDTO {
    private long totalLicenses;
    private long licensesLessThanZero;
    private long licensesGreaterThan45;
    private long licensesLessThan45;
    
    // Constructors, getters, and setters
}
