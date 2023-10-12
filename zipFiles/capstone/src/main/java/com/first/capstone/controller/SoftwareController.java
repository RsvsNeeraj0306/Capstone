package com.first.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.first.capstone.Entity.Manufacturer;
import com.first.capstone.Entity.Software;
import com.first.capstone.Services.ManufacturerService;
import com.first.capstone.Services.SoftwareLicenseHistoryService;
import com.first.capstone.Services.SoftwareService;


@RestController
@RequestMapping("/api")
public class SoftwareController {


    @Autowired
    private SoftwareLicenseHistoryService softwareLicenseHistoryService;

    @Autowired
    private SoftwareService softwareService;

    @Autowired
    private ManufacturerService manufacturerService;



    @GetMapping("/helloSoftware")
    public String getHello() {
        return ("welcome to my Software project");
    }


    @PostMapping("/addSoftware")
    public Software addSoftware(@RequestBody Software software) {
    Manufacturer manufacturer = manufacturerService.getOrCreateManufacturer(manufacturerService.getAllManufacturers().get(0));
  
    software.setManufacturer(manufacturer);
    software.setSoftwareName(software.getSoftwareName());
    software.setPurchaseDate(software.getPurchaseDate());
    software.setExpiryDate(software.getExpiryDate());
    software.setTypeOfPlan(software.getTypeOfPlan());
    software.setUsersCanUse(software.getUsersCanUse());
    software.setPriceOfSoftware(software.getPriceOfSoftware());
    softwareService.saveSoftware(software);

    return software;
}

    
}
