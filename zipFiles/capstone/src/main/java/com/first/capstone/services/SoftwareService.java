package com.first.capstone.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.first.capstone.dto.LicenseCountDTO;
import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.dto.SoftwareDeviceDTO;
import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.Software;
import com.first.capstone.entity.SoftwareLicenseHistory;
import com.first.capstone.respositories.SoftwareLicenseHistoryRepository;
import com.first.capstone.respositories.SoftwareRepository;

@Service
public class SoftwareService {

    private final SoftwareRepository softwareRepository;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private SoftwareLicenseHistoryRepository softwareLicenseHistoryRepository;

    @Autowired
    public SoftwareService(
            SoftwareRepository softwareRepository,
            ManufacturerService manufacturerService,
            SoftwareLicenseHistoryRepository softwareLicenseHistoryRepository) {
        this.softwareRepository = softwareRepository;
        this.manufacturerService = manufacturerService;
        this.softwareLicenseHistoryRepository = softwareLicenseHistoryRepository;
    }


    public List<Software> findAllSoftware() {
        return softwareRepository.findAll();
    }

    public Software saveSoftware(Software software) {
        return softwareRepository.save(software);
    }

    public Software getOrCreateNewSoftware(Software software) {
        return softwareRepository.findByIdAndSoftwareName(software.getId(), software.getSoftwareName())
                .orElseGet(() -> {
                    Software newSoftware = new Software();
                    newSoftware.setSoftwareName(software.getSoftwareName());
                    newSoftware.setManufacturer(software.getManufacturer());
                    newSoftware.setPurchaseDate(software.getPurchaseDate());
                    newSoftware.setExpiryDate(software.getExpiryDate());
                    newSoftware.setUsersCanUse(software.getUsersCanUse());
                    newSoftware.setPriceOfSoftware(software.getPriceOfSoftware());
                    newSoftware.setTypeOfPlan(software.getTypeOfPlan());
                    return softwareRepository.save(newSoftware);
                });
    }

    public ResponseEntity<ResponseDTO> addSoftware(@RequestBody SoftwareDeviceDTO softwareDeviceDTO) {
        Manufacturer manufacturer = manufacturerService.getOrCreateManufacturer(softwareDeviceDTO.getManufacturer());

        Software software = softwareDeviceDTO.getSoftware();
        SoftwareLicenseHistory softwareLicenseHistory = softwareDeviceDTO.getSoftwareLicenseHistory();
        ResponseDTO responseDTO = new ResponseDTO();
        if (software != null) {
            software.setManufacturer(manufacturer);

            // Correct the setters for software properties
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

                // Correct the setter for license key
                softwareLicenseHistory.setLicenseKey(softwareLicenseHistory.getLicenseKey());

                // Save the software license history to the database
                softwareLicenseHistoryRepository.save(softwareLicenseHistory);
            }
            responseDTO.setResponseBody("Software added successfully");
            return ResponseEntity.ok().body(responseDTO);
        } 
        else {
            responseDTO.setResponseBody("Software not found");
            return ResponseEntity.badRequest().body(responseDTO);

        }
    }

    public ResponseEntity<ResponseDTO> addLicenseHistory(@RequestBody SoftwareDeviceDTO softwareDeviceDTO) {
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
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody("License history added successfully");
            return ResponseEntity.ok().body(responseDTO);
        } else {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody("Software not found");
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    public ResponseEntity<Map<String, Long>> getLicenseCounts() {
        // Retrieve licenses from the database
        List<Software> licenses = softwareRepository.findAll();

        // Calculate the counts based on criteria
        long totalLicenses = licenses.size();

        long licensesLessThanZeroCount = licenses.stream()
                .filter(license -> {
                    LocalDate expiryDate = license.getExpiryDate().toLocalDate();
                    LocalDate today = LocalDate.now();
                    long daysDifference = ChronoUnit.DAYS.between(today, expiryDate);
                    return daysDifference < 0;
                })
                .count();

        long licensesGreaterThan45Count = licenses.stream()
                .filter(license -> {
                    LocalDate today = LocalDate.now();
                    LocalDate expiryDate = license.getExpiryDate().toLocalDate();
                    long daysDifference = ChronoUnit.DAYS.between(today, expiryDate);
                    return daysDifference > 45;
                })
                .count();

        long licensesLessThan45Count = licenses.stream()
                .filter(license -> {
                    LocalDate today = LocalDate.now();
                    LocalDate expiryDate = license.getExpiryDate().toLocalDate();
                    long daysDifference = ChronoUnit.DAYS.between(today, expiryDate);
                    return daysDifference >= 0 && daysDifference <= 45;
                })
                .count();

        LicenseCountDTO licenseCountDTO = new LicenseCountDTO();
        licenseCountDTO.setTotalLicenses(totalLicenses);
        licenseCountDTO.setLicensesLessThanZero(licensesLessThanZeroCount);
        licenseCountDTO.setLicensesGreaterThan45(licensesGreaterThan45Count);
        licenseCountDTO.setLicensesLessThan45(licensesLessThan45Count);

        Map<String, Long> counts = new HashMap<>();
        counts.put("totalLicenses", totalLicenses);
        counts.put("licensesLessThanZero", licensesLessThanZeroCount);
        counts.put("licensesGreaterThan45", licensesGreaterThan45Count);
        counts.put("licensesLessThan45Count", licensesLessThan45Count);

        return new ResponseEntity<>(counts, HttpStatus.OK);
    }

    // Implement other service methods as needed
}
