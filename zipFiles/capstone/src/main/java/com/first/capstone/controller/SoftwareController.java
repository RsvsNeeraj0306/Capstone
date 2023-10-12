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
import com.first.capstone.Entity.SoftwareLicenseHistory;
import com.first.capstone.Respositories.SoftwareLicenseHistoryRepository;
import com.first.capstone.Respositories.SoftwareRepository;
import com.first.capstone.Services.ManufacturerService;
import com.first.capstone.dto.SoftwareDeviceDTO;



@RestController
@RequestMapping("/api")
public class SoftwareController {


    @Autowired
    private SoftwareLicenseHistoryRepository softwareLicenseHistoryRepository;

    @Autowired
    private SoftwareRepository softwareRepository;

    @Autowired
    private ManufacturerService manufacturerService;


    @GetMapping("/helloSoftware")
    public String getHello() {
        return ("welcome to my Software project");
    }

    @PostMapping("/addSoftware")
    public ResponseEntity<String> addSoftware(@RequestBody SoftwareDeviceDTO softwareDeviceDTO) {
    Manufacturer manufacturer = manufacturerService.getOrCreateManufacturer(softwareDeviceDTO.getManufacturer());
  
    Software software = softwareDeviceDTO.getSoftware();
    SoftwareLicenseHistory softwareLicenseHistory = softwareDeviceDTO.getSoftwareLicenseHistory();
    System.out.println(software);
    if (software != null) 
    {
        software.setManufacturer(manufacturer);
        software.setSoftwareName(software.getSoftwareName());
        software.setPurchaseDate(software.getPurchaseDate());
        software.setExpiryDate(software.getExpiryDate());
        software.setTypeOfPlan(software.getTypeOfPlan());
        software.setUsersCanUse(software.getUsersCanUse());
        software.setPriceOfSoftware(software.getPriceOfSoftware());
        softwareRepository.save(software);

        if (softwareLicenseHistory != null) {
        // Set the software for the software license history
        softwareLicenseHistory.setSoftware(software);
        softwareLicenseHistory.setLicenseKey(softwareLicenseHistory.getLicenseKey());
        // Save the software license history to the database
        softwareLicenseHistoryRepository.save(softwareLicenseHistory);
        }
        return new ResponseEntity<>("Software added successfully", HttpStatus.CREATED);
    } 
        else 
        {
        return new ResponseEntity<>("Invalid or missing Software information", HttpStatus.BAD_REQUEST);
        }
    }

    
     @PostMapping("/addSoftwareHistory")
    public ResponseEntity<String> addLicenseHistory(@RequestBody SoftwareDeviceDTO softwareDeviceDTO) {
   
    Software software = softwareDeviceDTO.getSoftware();
    Software existingSoftware = softwareRepository.findById(software.getId()).orElse(null);
    
    if (existingSoftware != null) {
        // Update the purchase date and expiry date
        existingSoftware.setPurchaseDate(software.getPurchaseDate());
        existingSoftware.setExpiryDate(software.getExpiryDate());
        

        SoftwareLicenseHistory softwareLicenseHistory = new SoftwareLicenseHistory();
        softwareLicenseHistory.setSoftware(existingSoftware);
        softwareLicenseHistory.setLicenseKey(softwareDeviceDTO.getSoftwareLicenseHistory().getLicenseKey());
        
        // Save the updated software and the new software license history
        softwareRepository.save(existingSoftware);
        softwareLicenseHistoryRepository.save(softwareLicenseHistory);
        
        return new ResponseEntity<>("License history added successfully", HttpStatus.CREATED);
    } else {
        return new ResponseEntity<>("Software not found", HttpStatus.BAD_REQUEST);
    }
}

}