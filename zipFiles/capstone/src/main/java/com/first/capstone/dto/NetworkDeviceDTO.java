package com.first.capstone.dto;



import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.NetworkDevice;
import com.first.capstone.entity.NetworkDeviceAnalysis;
import com.first.capstone.entity.NetworkDeviceRMA;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NetworkDeviceDTO {
    private NetworkDevice networkDevice;
    private Manufacturer manufacturer;
    private NetworkDeviceAnalysis networkDeviceAnalysis;
    private NetworkDeviceRMA networkDeviceRMA;
    // Constructors, getters, and setters
}


