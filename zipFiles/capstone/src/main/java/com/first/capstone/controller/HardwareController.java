package com.first.capstone.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.first.capstone.dto.NetworkDeviceDTO;
import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.entity.NetworkDevice;

import com.first.capstone.services.NetworkDeviceService;

import jakarta.transaction.Transactional;

@CrossOrigin()
@RestController
@RequestMapping("/api")
public class HardwareController {


  @Autowired
  private NetworkDeviceService networkDeviceService;



  @GetMapping("/hello")
  public String getHello() {
    return ("welcome to my project");
  }

  @GetMapping("/allHardware")
  public List<NetworkDevice> getAllNetworkDevices() {
    return networkDeviceService.findAllNetworkDevices();
  }

  @PostMapping("/addNetworkDevices")
  @Transactional
  public ResponseEntity<ResponseDTO> addNetworkDevice(@RequestBody NetworkDeviceDTO networkDeviceDTO) {
    return networkDeviceService.addNetworkDevice(networkDeviceDTO);
  }


  @PostMapping("/addNetworkDevicesHistory")
  @Transactional
  public ResponseEntity<ResponseDTO> addNetworkDeviceHistory(@RequestBody NetworkDeviceDTO networkDeviceDTO) {
    return networkDeviceService.addNetworkDeviceHistory(networkDeviceDTO);
  }


      
}
