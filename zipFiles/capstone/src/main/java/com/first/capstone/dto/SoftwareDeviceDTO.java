package com.first.capstone.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.first.capstone.Entity.Manufacturer;
import com.first.capstone.Entity.Software;
import com.first.capstone.Entity.SoftwareLicenseHistory;

import lombok.Data;

@Data
public class SoftwareDeviceDTO {
    private Software software;
    private SoftwareLicenseHistory softwareLicenseHistory;
    private Manufacturer manufacturer;

    // Constructors, getters, and setters
}

