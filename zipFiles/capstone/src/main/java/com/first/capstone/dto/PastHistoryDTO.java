package com.first.capstone.dto;

import java.util.Date;

import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.NetworkDevice;
import com.first.capstone.entity.Software;

import lombok.Data;

@Data
public class PastHistoryDTO {

    private Date pastRepairDate;
    private Date pastReplacemntDate;
    private Integer pastRepairCost;
    private Date pastRefunDate;
    private Integer pastRefundAmount; 
    private Manufacturer manufacturer;
    private Software software;
    private NetworkDevice networkDevice;
    
}
