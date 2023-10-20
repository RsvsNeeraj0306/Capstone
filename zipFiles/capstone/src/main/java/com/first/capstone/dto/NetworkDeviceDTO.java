package com.first.capstone.dto;

import java.math.BigDecimal;
import java.sql.Date;


import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.NetworkDevice;
import com.first.capstone.entity.NetworkDevicesHistory;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NetworkDeviceDTO {
    private NetworkDevice networkDevice;
    private String hardwareName;
    private Date purchaseDate;
    private Date warrantyEndDate;
    private String serialNumber;
    private Manufacturer manufacturer;
    private String location;
    private Integer quantity;
    private BigDecimal cost;
    private NetworkDevicesHistory networkDevicesHistory;
   
    // Constructors, getters, and setters
}


