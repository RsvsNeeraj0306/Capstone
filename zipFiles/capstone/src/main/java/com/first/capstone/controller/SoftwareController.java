package com.first.capstone.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<ResponseDTO> addSoftware(@RequestBody SoftwareDeviceDTO softwareDeviceDTO) {
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
    public ResponseEntity<ResponseDTO> changePlan(@RequestBody SoftwareDeviceDTO softwareDeviceDTO) {
        return softwareService.changePlan(softwareDeviceDTO); // Update method call.
    }

}