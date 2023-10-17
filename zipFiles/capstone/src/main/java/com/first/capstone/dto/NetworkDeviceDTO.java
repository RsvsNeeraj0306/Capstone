package com.first.capstone.dto;

import java.sql.Date;

import com.first.capstone.entity.DeviceType;
import com.first.capstone.entity.Location;
import com.first.capstone.entity.Manufacturer;

import lombok.Data;

@Data
public class NetworkDeviceDTO {
    private String hardwareName;
    private Date purchaseDate;
    private Date warrantyEndDate;
    private DeviceType deviceType;
    private Location location;
    private String serialNumber;
    private Manufacturer manufacturer;

    // Constructors, getters, and setters
}
