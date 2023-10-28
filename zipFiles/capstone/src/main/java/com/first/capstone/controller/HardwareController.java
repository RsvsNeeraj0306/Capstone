package com.first.capstone.controller;

import java.util.List;
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

import com.first.capstone.dto.NetworkDeviceDTO;
import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.entity.NetworkDevice;
import com.first.capstone.entity.NetworkDeviceAnalysis;
import com.first.capstone.entity.NetworkDeviceRMA;
import com.first.capstone.entity.NetworkDevicesHistory;
import com.first.capstone.services.NetworkDeviceService;

import jakarta.transaction.Transactional;

@CrossOrigin()
@RestController
@RequestMapping("/api")
public class HardwareController {


  @Autowired
  private NetworkDeviceService networkDeviceService;

 
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
    return networkDeviceService.addNetworkDeviceHistory(networkDeviceDTO.getNetworkDevice(), "ADD");
  }

  @PostMapping("/setRMA")
  @Transactional
  public ResponseEntity<NetworkDeviceRMA> setRMA(@RequestBody NetworkDeviceDTO networkDeviceDTO) {
    return networkDeviceService.setNetworkDeviceRMA(networkDeviceDTO);
  }

  @PostMapping("/setAnalysis")
  @Transactional
  public ResponseEntity<ResponseDTO> setAnalysis(@RequestBody NetworkDeviceDTO networkDeviceDTO) {
    return networkDeviceService.setNetworkDeviceAnalysis(networkDeviceDTO);
  }

  @GetMapping("/getNetworkDeviceHistory")
  public List<NetworkDevicesHistory> getNetworkDeviceHistory() {
    return networkDeviceService.getNetworkDeviceHistory();
  }

  @DeleteMapping("/deleteNetworkDevice/{id}")
  public ResponseEntity<ResponseDTO> deleteNetworkDevice(@PathVariable Long id) {
    return networkDeviceService.deleteNetworkDeviceById(id);
  }

  @GetMapping("/getNetworkDeviceRMA")
  public List<NetworkDeviceRMA> getNetworkDeviceRMA() {
    return networkDeviceService.getNetworkDeviceRMA();
  }

  @GetMapping("/getNetworkDeviceAnalysis")
  public List<NetworkDeviceAnalysis> getNetworkDeviceAnalysis() {
    return networkDeviceService.getNetworkDeviceAnalysis();
  }

      
}
