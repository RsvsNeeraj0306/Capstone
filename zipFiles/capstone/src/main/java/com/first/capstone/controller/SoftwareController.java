package com.first.capstone.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.first.capstone.Entity.Manufacturer;
import com.first.capstone.Entity.Software;
import com.first.capstone.Entity.SoftwareLicenseHistory;
import com.first.capstone.Respositories.SoftwareLicenseHistoryRepository;
import com.first.capstone.Respositories.SoftwareRepository;
import com.first.capstone.Services.ManufacturerService;
import com.first.capstone.dto.LicenseCountDTO;
import com.first.capstone.dto.SoftwareDeviceDTO;


@CrossOrigin()
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



@GetMapping("/allSoftware")
public List<Software> getSoftwareByManufacturerFieldOfWork() {
    return softwareRepository.findAllByManufacturer();
}


@GetMapping("/calculate-differences")
public Map<Long, Long> calculateDifferences() {
    LocalDate currentDate = LocalDate.now();
    List<Software> licenses = softwareRepository.findAll();

    Map<Long, Long> daysDifferenceMap = licenses.stream().collect(Collectors.toMap(
        Software::getId, // Software ID
        license -> {
            LocalDate expiryDate = license.getExpiryDate().toLocalDate();
            return ChronoUnit.DAYS.between(currentDate, expiryDate);
        }
    ));

    return daysDifferenceMap;
}

@GetMapping("/licenseCounts")
public ResponseEntity<Map<String, Long>> getLicenseCounts() {
    // Retrieve licenses from the database
    List<Software> licenses = softwareRepository.findAll();

    // Calculate the counts based on criteria
    long totalLicenses = licenses.size();
    
    long licensesLessThanZeroCount = licenses.stream()
            .filter(license -> {
                LocalDate purchaseDate = license.getPurchaseDate().toLocalDate();
                LocalDate expiryDate = license.getExpiryDate().toLocalDate();
                long daysDifference = ChronoUnit.DAYS.between(purchaseDate, expiryDate);
                return daysDifference < 0;
            })
            .count();

    long licensesGreaterThan45Count = licenses.stream()
            .filter(license -> {
                LocalDate purchaseDate = license.getPurchaseDate().toLocalDate();
                LocalDate expiryDate = license.getExpiryDate().toLocalDate();
                long daysDifference = ChronoUnit.DAYS.between(purchaseDate, expiryDate);
                return daysDifference > 45;
            })
            .count();

    long licensesLessThan45Count = licenses.stream()
            .filter(license -> {
                LocalDate purchaseDate = license.getPurchaseDate().toLocalDate();
                LocalDate expiryDate = license.getExpiryDate().toLocalDate();
                long daysDifference = ChronoUnit.DAYS.between(purchaseDate, expiryDate);
                return daysDifference < 45;
            })
            .count();

            

        

    // Create a map to store the counts

    LicenseCountDTO licenseCountDTO = new LicenseCountDTO();
    licenseCountDTO.setTotalLicenses(totalLicenses);
    licenseCountDTO.setLicensesLessThanZero(licensesLessThanZeroCount);
    licenseCountDTO.setLicensesGreaterThan45(licensesGreaterThan45Count);
    licenseCountDTO.setLicensesLessThanZero(licensesLessThan45Count);

    Map<String, Long> counts = new HashMap<>();
    counts.put("totalLicenses", totalLicenses);
    counts.put("licensesLessThanZero", licensesLessThanZeroCount);
    counts.put("licensesGreaterThan45", licensesGreaterThan45Count);
    counts.put("licensesLessThan45Count",licensesLessThan45Count);


    return new ResponseEntity<>(counts, HttpStatus.OK);
}



}