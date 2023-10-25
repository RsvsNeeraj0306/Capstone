package com.first.capstone.dto;



import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.Software;
import com.first.capstone.entity.SoftwareAnalysis;
import com.first.capstone.entity.SoftwareRMA;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoftwareDeviceDTO {
    private Software software;
    private Manufacturer manufacturer;
    private SoftwareRMA softwareRMA;
    private SoftwareAnalysis softwareAnalysis;   // Constructors, getters, and setters
}

