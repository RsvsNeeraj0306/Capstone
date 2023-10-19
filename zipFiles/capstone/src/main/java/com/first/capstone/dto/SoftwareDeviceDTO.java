package com.first.capstone.dto;



import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.Software;
import com.first.capstone.entity.SoftwareAnalysis;
import com.first.capstone.entity.SoftwareLicenseHistory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoftwareDeviceDTO {
    private Software software;
    private SoftwareLicenseHistory softwareLicenseHistory;
    private Manufacturer manufacturer;
    private SoftwareAnalysis softwareAnalysis;   // Constructors, getters, and setters
}

