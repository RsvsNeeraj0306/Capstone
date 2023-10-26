package com.first.capstone.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.first.capstone.entity.SoftwareAnalysis;
import com.first.capstone.entity.SoftwareLicenseHistory;
import com.first.capstone.entity.SoftwareRMA;
import com.first.capstone.respositories.SoftwareLicenseHistoryRepository;
import com.first.capstone.respositories.SoftwareRMARepository;
import com.first.capstone.respositories.SoftwareRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SoftwareService {

    @Autowired
    private SoftwareRepository softwareRepository;

    @Autowired
    private final SoftwareRMARepository softwareRMARepository;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private GmailService gmailService;

    @Autowired
    private SoftwareLicenseHistoryRepository softwareLicenseHistoryRepository;

    private static final String ERROR_MESSAGE = "Software not found";

    enum Action {
        ADDED, RENEWED, CHANGED_PLAN, DELETED, REFUND, ANALYSIS
    }

    public SoftwareService(
            SoftwareRepository softwareRepository,
            ManufacturerService manufacturerService,
            SoftwareLicenseHistoryRepository softwareLicenseHistoryRepository,
            SoftwareRMARepository softwareRMARepository) {
        this.softwareRepository = softwareRepository;
        this.manufacturerService = manufacturerService;
        this.softwareLicenseHistoryRepository = softwareLicenseHistoryRepository;
        this.softwareRMARepository = softwareRMARepository;
    }

    public List<Software> findAllSoftware() {
        return softwareRepository.findAll();
    }

    public Software saveSoftware(Software software) {
        return softwareRepository.save(software);
    }

    public Software getOrCreateNewSoftware(Software software, Manufacturer manufacturer) {
        return softwareRepository.findByIdAndSoftwareName(manufacturer.getId(), software.getSoftwareName())//id:manufucturer id name:software name
                .orElseGet(() -> {
                    Software newSoftware = new Software();
                    newSoftware.setSoftwareName(software.getSoftwareName());
                    newSoftware.setManufacturer(manufacturer);
                    newSoftware.setPurchaseDate(software.getPurchaseDate());
                    newSoftware.setExpiryDate(software.getExpiryDate());
                    newSoftware.setVersion(software.getVersion());
                    newSoftware.setQuantity(software.getQuantity());
                    newSoftware.setPriceOfSoftware(software.getPriceOfSoftware());
                    newSoftware.setTypeOfPlan(software.getTypeOfPlan());
                    newSoftware.setLicenseKey(software.getLicenseKey());
                    return softwareRepository.save(newSoftware);
                });
    }

    public ResponseEntity<Software> addSoftware(@RequestBody SoftwareDeviceDTO softwareDeviceDTO) {
        Manufacturer manufacturer = manufacturerService.getOrCreateManufacturer(softwareDeviceDTO.getManufacturer());

        Software software = getOrCreateNewSoftware(softwareDeviceDTO.getSoftware(), manufacturer);
        addLicenseHistory(software, Action.ADDED.toString());
        return ResponseEntity.ok().body(software);
    }

    public ResponseEntity<ResponseDTO> renewSoftware(SoftwareDeviceDTO softwareDeviceDTO) {
        Software software = softwareDeviceDTO.getSoftware();
        Optional<Software> existingSoftware = softwareRepository.findById(software.getId());

        if (existingSoftware.isPresent()) {
            // Update the purchase date, expiry date, and license key
            existingSoftware.get().setPurchaseDate(software.getPurchaseDate());
            existingSoftware.get().setExpiryDate(software.getExpiryDate());
            existingSoftware.get().setLicenseKey(software.getLicenseKey());
            saveSoftware(existingSoftware.get());

            addLicenseHistory(existingSoftware.get(), Action.RENEWED.toString());

            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody("Software renewed successfully");
            return ResponseEntity.ok().body(responseDTO);
        } else {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody(ERROR_MESSAGE);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    public ResponseEntity<Software> changePlan(@RequestBody SoftwareDeviceDTO softwareDeviceDTO) {
        Software software = softwareDeviceDTO.getSoftware();
        Optional<Software> existingSoftware = softwareRepository.findById(software.getId());

        if (existingSoftware.isPresent()) {
            existingSoftware.get().setLicenseKey(software.getLicenseKey());
            existingSoftware.get().setExpiryDate(software.getExpiryDate());
            existingSoftware.get().setPurchaseDate(software.getPurchaseDate());
            existingSoftware.get().setTypeOfPlan(software.getTypeOfPlan());
            existingSoftware.get().setQuantity(software.getQuantity());
            existingSoftware.get().setPriceOfSoftware(software.getPriceOfSoftware());
            saveSoftware(existingSoftware.get());

            addLicenseHistory(existingSoftware.get(), Action.CHANGED_PLAN.toString());

            return ResponseEntity.ok().body(existingSoftware.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<ResponseDTO> refundSoftware(SoftwareDeviceDTO softwareDeviceDTO) {
        Software software = softwareDeviceDTO.getSoftware();
        Optional<Software> existingSoftware = softwareRepository.findById(software.getId());
        SoftwareRMA softwareRMA = new SoftwareRMA();
        if (existingSoftware.isPresent()) {
            softwareRMA.setRefunDate(softwareRMA.getRefunDate());
            softwareRMA.setRefundAmount(software.getPriceOfSoftware());
            softwareRMA.setRefundReason(softwareRMA.getRefundReason());
            softwareRMA.setSoftware(software);
            addLicenseHistory(existingSoftware.get(), Action.REFUND.toString());
            softwareRMARepository.save(softwareRMA);

            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody("Software refunded successfully");
            return ResponseEntity.ok().body(responseDTO);

        } else {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody(ERROR_MESSAGE);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    public ResponseEntity<ResponseDTO> setSoftwareAnalysis(SoftwareDeviceDTO softwareDeviceDTO) {
        Software software = softwareDeviceDTO.getSoftware();
        Optional<Software> existingSoftware = softwareRepository.findById(software.getId());
        SoftwareAnalysis softwareAnalysis = new SoftwareAnalysis();
        if (existingSoftware.isPresent()) {
            softwareAnalysis.setSoftware(existingSoftware.get());
            softwareAnalysis.setActiveUsers(softwareAnalysis.getActiveUsers());
            softwareAnalysis.setAverageTimeUsage(softwareAnalysis.getAverageTimeUsage());
            softwareAnalysis.setCompanyRating(softwareAnalysis.getCompanyRating());
            addLicenseHistory(existingSoftware.get(), Action.ANALYSIS.toString());
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody("Software Analysis added successfully");
            return ResponseEntity.ok().body(responseDTO);

        } else {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody(ERROR_MESSAGE);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    public ResponseEntity<ResponseDTO> deleteSoftwareById(Long softwareId) {
        // Retrieve the software by its ID
        Optional<Software> softwareOptional = softwareRepository.findById(softwareId);

        if (softwareOptional.isPresent()) {
            addLicenseHistory(softwareOptional.get(), Action.DELETED.toString());
            softwareRepository.delete(softwareOptional.get());

            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody("Software deleted successfully");
            return ResponseEntity.ok().body(responseDTO);
        } else {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody(ERROR_MESSAGE);
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    public ResponseEntity<ResponseDTO> addLicenseHistory(@RequestBody Software software, String action) {

        SoftwareLicenseHistory softwareLicenseHistory = new SoftwareLicenseHistory();
        softwareLicenseHistory.setSoftwareNameAndId(software.getSoftwareName());
        softwareLicenseHistory.setLicenseKey(software.getLicenseKey());
        softwareLicenseHistory.setExpiryDate(software.getExpiryDate());
        softwareLicenseHistory.setPurchaseDate(software.getPurchaseDate());
        softwareLicenseHistory.setPriceOfSoftware(software.getPriceOfSoftware());
        softwareLicenseHistory.setTypeOfPlan(software.getTypeOfPlan());
        softwareLicenseHistory.setQuantity(software.getQuantity());
        softwareLicenseHistory.setVersion(software.getVersion());
        softwareLicenseHistory.setAction(action);

        softwareLicenseHistoryRepository.save(softwareLicenseHistory);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseBody("License history added successfully");
        return ResponseEntity.ok().body(responseDTO);
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
                    return daysDifference <= 0;
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
                    return daysDifference >= 1 && daysDifference <= 45;
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

    public ResponseEntity<List<Software>> getSoftwareLessThan45days() {
        List<Software> licenses = softwareRepository.findAll();
        List<Software> listOfSoftwareLessThan45Days = licenses.stream()
                .filter(license -> {
                    LocalDate today = LocalDate.now();
                    LocalDate expiryDate = license.getExpiryDate().toLocalDate();
                    long daysDifference = ChronoUnit.DAYS.between(today, expiryDate);
                    return daysDifference >= 1 && daysDifference <= 45;
                })
                .toList();
        if (listOfSoftwareLessThan45Days != null) {
            return ResponseEntity.ok().body(listOfSoftwareLessThan45Days);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

     public List<Software> getSoftwareLessThanZeroDays() {
        List<Software> licenses = softwareRepository.findExpiredSoftware(LocalDate.now());
        if(!licenses.isEmpty()) {
            gmailService.sendLicenseExpirationEmail(licenses);
            return licenses;
        } else {
            return new ArrayList<>();
        }
        
    }

}
