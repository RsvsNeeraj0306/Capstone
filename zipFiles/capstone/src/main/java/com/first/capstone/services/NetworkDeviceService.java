package com.first.capstone.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.first.capstone.dto.NetworkDeviceDTO;
import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.NetworkDevice;
import com.first.capstone.entity.NetworkDevicesHistory;

import com.first.capstone.respositories.NetworkDeviceRepository;
import com.first.capstone.respositories.NetworkDevicesHistoryRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class NetworkDeviceService {

    @Autowired
    private NetworkDeviceRepository networkDeviceRepository;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private NetworkDevicesHistoryRepository networkDeviceHistoryRepository;

    enum Action {
        ADD, UPDATE, DELETED
    }

    public NetworkDeviceService(NetworkDeviceRepository networkDeviceRepository,
            ManufacturerService manufacturerService, NetworkDevicesHistoryRepository networkDeviceHistoryRepository) {
        this.networkDeviceRepository = networkDeviceRepository;
        this.manufacturerService = manufacturerService;
        this.networkDeviceHistoryRepository = networkDeviceHistoryRepository;
    }

    public List<NetworkDevice> findAllNetworkDevices() {
        return networkDeviceRepository.findAll();
    }

    public NetworkDevice saveNetworkDevice(NetworkDevice networkDevice) {
        return networkDeviceRepository.save(networkDevice);
    }

    public NetworkDevice getOrCreaNetworkDevice(NetworkDevice networkDevice, Manufacturer manufacturer) {
        return networkDeviceRepository.findByIdAndHardwareName(manufacturer.getId(), networkDevice.getHardwareName())
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
                    return networkDeviceRepository.save(newNetworkDevice);
                });
    }

    
    public ResponseEntity<ResponseDTO> addNetworkDevice(@RequestBody NetworkDeviceDTO networkDeviceDTO) {
        Manufacturer manufacturer = manufacturerService.getOrCreateManufacturer(networkDeviceDTO.getManufacturer());

        NetworkDevice networkDevice = getOrCreaNetworkDevice(networkDeviceDTO.getNetworkDevice(), manufacturer);
        addNetworkDeviceHistory(networkDevice, Action.ADD.toString());

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseBody("Network device added successfully");
        return ResponseEntity.ok().body(responseDTO);
    }

    public ResponseEntity<ResponseDTO> addNetworkDeviceHistory(@RequestBody NetworkDevice networkDevice, String action) {

        NetworkDevicesHistory networkDevicesHistory = new NetworkDevicesHistory();
        networkDevicesHistory.setDeviceNameAndId(networkDevice.getHardwareName()+ " ID: " + networkDevice.getId());
        networkDevicesHistory.setPurchaseDate(networkDevice.getPurchaseDate());
        networkDevicesHistory.setWarrantyEndDate(networkDevice.getWarrantyEndDate());
        networkDevicesHistory.setSerialNumber(networkDevice.getSerialNumber());
        networkDevicesHistory.setCost(networkDevice.getCost());
        networkDevicesHistory.setQuantity(networkDevice.getQuantity());
        networkDevicesHistory.setAction(action);

        networkDeviceHistoryRepository.save(networkDevicesHistory);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseBody("Network device history added successfully");
        return ResponseEntity.ok().body(responseDTO);

    }

    public ResponseEntity<ResponseDTO> deleteNetworkDeviceById(Long id) {
        Optional<NetworkDevice> networkDevice = networkDeviceRepository.findById(id);
        if (networkDevice.isPresent()) {

            addNetworkDeviceHistory(networkDevice.get(), Action.DELETED.toString());    
            networkDeviceRepository.deleteById(networkDevice.get().getId());
            
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody("Network device deleted successfully");
            return ResponseEntity.ok(responseDTO);
        } else {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody("Network device not found");
            return ResponseEntity.ok(responseDTO);
        }
    }

}
