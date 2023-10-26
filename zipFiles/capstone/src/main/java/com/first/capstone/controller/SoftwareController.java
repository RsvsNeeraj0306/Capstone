package com.first.capstone.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.dto.SoftwareDeviceDTO;

import com.first.capstone.entity.Software;

import com.first.capstone.respositories.SoftwareRepository;

import com.first.capstone.services.SoftwareService;

@CrossOrigin()
@RestController
@RequestMapping("/api")
public class SoftwareController {

    @Autowired
    private SoftwareRepository softwareRepository;

    @Autowired
    private SoftwareService softwareService;

    @GetMapping("/helloSoftware")
    public String getHello() {
        return ("welcome to my Software project");
    }

    @PostMapping("/addSoftware")
    public ResponseEntity<Software> addSoftware(@RequestBody SoftwareDeviceDTO softwareDeviceDTO) {
        return softwareService.addSoftware(softwareDeviceDTO);
    }

    @GetMapping("/SelectedSoftware")
    public List<Software> getSoftwareByManufacturerFieldOfWork() {
        return softwareRepository.findAllByManufacturer();
    }

    @GetMapping("/allSoftware")
    public List<Software> getAllSoftware() {
        return softwareRepository.findAll();
    }

    @GetMapping("/licenseCounts")
    public ResponseEntity<Map<String, Long>> getLicenseCounts() {
        return softwareService.getLicenseCounts();
    }

    @PostMapping("/renewSoftware")
    public ResponseEntity<ResponseDTO> renewSoftware(@RequestBody SoftwareDeviceDTO softwareDeviceDTO) {
        return softwareService.renewSoftware(softwareDeviceDTO);
    }

    @PostMapping("/changePlan") // Update the API endpoint name
    public ResponseEntity<Software> changePlan(@RequestBody SoftwareDeviceDTO softwareDeviceDTO) {
        return softwareService.changePlan(softwareDeviceDTO); // Update method call.
    }

    @DeleteMapping("/deleteSoftware/{softwareId}")
    public ResponseEntity<ResponseDTO> deleteSoftwareById(@PathVariable Long softwareId) {
        return softwareService.deleteSoftwareById(softwareId);
    }

    @GetMapping("/getSoftwareLessThan45days")
    public ResponseEntity<List<Software>> getSoftwareLessThan45day()
    {
        return softwareService.getSoftwareLessThan45days();
    }

    @GetMapping("/getSoftwareLessThanZerodays")
    public List<Software> getSoftwareLessThanZeroday()
    {
            return softwareService.getSoftwareLessThanZeroDays();
    }

   

}