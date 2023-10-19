package com.first.capstone.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.first.capstone.dto.NetworkDeviceDTO;

import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.NetworkDevice;

import com.first.capstone.respositories.NetworkDeviceRepository;
import com.first.capstone.services.ManufacturerService;
import com.first.capstone.services.NetworkDeviceService;

import jakarta.transaction.Transactional;

@CrossOrigin()
@RestController
@RequestMapping("/api")
public class HardwareController {




  @Autowired
  private ManufacturerService manufacturerService;

  @Autowired
  private NetworkDeviceService networkDeviceService;

  @Autowired
  private NetworkDeviceRepository networkDeviceRepository;

  @GetMapping("/hello")
  public String getHello() {
    return ("welcome to my project");
  }

  @PostMapping("/addManufacture")
  public ResponseEntity<String> addManufacturer(@RequestBody Manufacturer manufacturer) {
    if (manufacturerService.manufacturerExists(manufacturer.getName(), manufacturer.getFieldOfWork())) {
      return ResponseEntity.badRequest().body("Manufacturer already exists.");
    }

    Manufacturer savedManufacturer = manufacturerService.saveManufacturer(manufacturer);
    return ResponseEntity.ok("Manufacturer added successfully with ID: " + savedManufacturer.getId());
  }

  @GetMapping("/allManufacture")
  public ResponseEntity<List<Manufacturer>> getAllManufacturers() {
    List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
    if (manufacturers.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(manufacturers);
    }
    return ResponseEntity.ok(manufacturers);
  }

  @GetMapping("/allHardware")
  public List<NetworkDevice> getAllNetworkDevices() {
    return networkDeviceRepository.findAllNetworkDevicesByManufacturer();
  }

  @PostMapping("/addNetwork-devices")
  @Transactional
  public ResponseEntity<String> addNetworkDevice(@RequestBody NetworkDeviceDTO networkDeviceDTO) {
    Manufacturer manufacturer = manufacturerService.getOrCreateManufacturer(networkDeviceDTO.getManufacturer());
    NetworkDevice networkDevice = networkDeviceService.getOrCreaNetworkDevice(networkDeviceDTO.getNetworkDevice());
    networkDevice.setManufacturer(manufacturer);
    networkDevice.setHardwareName(networkDeviceDTO.getHardwareName());
    networkDevice.setPurchaseDate(networkDeviceDTO.getPurchaseDate());
    networkDevice.setWarrantyEndDate(networkDeviceDTO.getWarrantyEndDate());
    networkDevice.setSerialNumber(networkDeviceDTO.getSerialNumber());

    return new ResponseEntity<>("Network device added successfully", HttpStatus.CREATED);
  }


      
}
