package com.first.capstone.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.first.capstone.dto.NetworkDeviceDTO;
import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.NetworkDevice;
import com.first.capstone.entity.NetworkDevicesHistory;
import com.first.capstone.respositories.NetworkDevicePastHistoryRepository;
import com.first.capstone.respositories.NetworkDeviceRepository;
import com.first.capstone.respositories.NetworkDevicesHistoryRepository;

import jakarta.transaction.Transactional;

@Service
public class NetworkDeviceService {

    private final NetworkDeviceRepository networkDeviceRepository;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private NetworkDevicesHistoryRepository networkDeviceHistoryRepository;


    public NetworkDeviceService(NetworkDeviceRepository networkDeviceRepository,ManufacturerService manufacturerService,NetworkDevicesHistoryRepository networkDeviceHistoryRepository) {
        this.networkDeviceRepository = networkDeviceRepository;
        this.manufacturerService = manufacturerService;
        this.networkDeviceHistoryRepository = networkDeviceHistoryRepository;
    }

    public List<NetworkDevice> findAllNetworkDevices() {
        return networkDeviceRepository.findAll();
    }

    @Transactional
    public NetworkDevice saveNetworkDevice(NetworkDevice networkDevice) {
        return networkDeviceRepository.save(networkDevice);
    }

    public NetworkDevice getOrCreaNetworkDevice(NetworkDevice networkDevice, Manufacturer manufacturer) {
        return networkDeviceRepository.findByIdAndHardwareName(networkDevice.getId(), networkDevice.getHardwareName())
                .orElseGet(() -> {
                   NetworkDevice newNetworkDevice = new NetworkDevice();
            newNetworkDevice.setHardwareName(networkDevice.getHardwareName());
            newNetworkDevice.setManufacturer(manufacturer);
            newNetworkDevice.setPurchaseDate(networkDevice.getPurchaseDate());
            newNetworkDevice.setSerialNumber(networkDevice.getSerialNumber());
            newNetworkDevice.setWarrantyEndDate(networkDevice.getWarrantyEndDate());
            newNetworkDevice.setLocation(networkDevice.getLocation());
            newNetworkDevice.setQuantity(networkDevice.getQuantity());
            newNetworkDevice.setCost(networkDevice.getCost());
            return saveNetworkDevice(networkDevice);
                });
    }


  public ResponseEntity<ResponseDTO> addNetworkDevice(@RequestBody NetworkDeviceDTO networkDeviceDTO) {
    Manufacturer manufacturer = manufacturerService.getOrCreateManufacturer(networkDeviceDTO.getManufacturer());
    NetworkDevice networkDevice = getOrCreaNetworkDevice(networkDeviceDTO.getNetworkDevice(), manufacturer);
    NetworkDevicesHistory networkDevicesHistory = networkDeviceDTO.getNetworkDevicesHistory();

    ResponseDTO responseDTO = new ResponseDTO();
    if(networkDevicesHistory != null) {
      networkDevicesHistory.setNetworkDevice(networkDevice);
      networkDevicesHistory.setPurchaseDate(networkDevice.getPurchaseDate());
      networkDevicesHistory.setWarrantyEndDate(networkDevice.getWarrantyEndDate());

      networkDeviceHistoryRepository.save(networkDevicesHistory);

    }
    responseDTO.setResponseBody("Network device added successfully");
    return ResponseEntity.ok().body(responseDTO);
  }


  public ResponseEntity<ResponseDTO> addNetworkDeviceHistory(@RequestBody NetworkDeviceDTO networkDeviceDTO) {
    NetworkDevice networkDevice= networkDeviceDTO.getNetworkDevice();
    NetworkDevice exisNetworkDevice= networkDeviceRepository.findById(networkDevice.getId()).orElse(null);
    if (exisNetworkDevice == null) {
            ResponseDTO responseDTO = new ResponseDTO();  
            responseDTO.setResponseBody("Network device not found");
            return ResponseEntity.ok().body(responseDTO);
        }
            NetworkDevicesHistory networkDevicesHistory = new NetworkDevicesHistory();
            networkDevicesHistory.setNetworkDevice(exisNetworkDevice);
            networkDevicesHistory.setPurchaseDate(exisNetworkDevice.getPurchaseDate());
            networkDevicesHistory.setWarrantyEndDate(exisNetworkDevice.getWarrantyEndDate());

            exisNetworkDevice.setPurchaseDate(networkDevice.getPurchaseDate());
            exisNetworkDevice.setWarrantyEndDate(networkDevice.getWarrantyEndDate());

            networkDeviceRepository.save(exisNetworkDevice);
            networkDeviceHistoryRepository.save(networkDevicesHistory);

            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody("Network device added successfully");
            return ResponseEntity.ok().body(responseDTO);
   
    }

  }


    

