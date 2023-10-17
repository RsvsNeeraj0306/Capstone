package com.first.capstone.dto;



import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.Software;
import com.first.capstone.entity.SoftwareLicenseHistory;

import lombok.Data;

@Data
public class SoftwareDeviceDTO {
    private Software software;
    private SoftwareLicenseHistory softwareLicenseHistory;
    private Manufacturer manufacturer;

    // Constructors, getters, and setters
}

