package com.first.capstone.dto;



import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.NetworkDevice;
import com.first.capstone.entity.NetworkDevicesHistory;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NetworkDeviceDTO {
    private NetworkDevice networkDevice;
    private Manufacturer manufacturer;
    private NetworkDevicesHistory networkDevicesHistory;
   
    // Constructors, getters, and setters
}


