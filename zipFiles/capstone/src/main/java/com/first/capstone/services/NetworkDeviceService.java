package com.first.capstone.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.first.capstone.entity.NetworkDevice;
import com.first.capstone.respositories.NetworkDeviceRepository;

import jakarta.transaction.Transactional;

@Service
public class NetworkDeviceService {

    @Autowired
    NetworkDeviceRepository networkDeviceRepository;

    public NetworkDeviceService(NetworkDeviceRepository networkDeviceRepository) {
        this.networkDeviceRepository = networkDeviceRepository;
    }

    public List<NetworkDevice> findAllNetworkDevices() {
        return networkDeviceRepository.findAll();
    }

    @Transactional
    public NetworkDevice saveNetworkDevice(NetworkDevice networkDevice) {
        return networkDeviceRepository.save(networkDevice);
    }

    public NetworkDevice getOrCreaNetworkDevice(NetworkDevice networkDevice) {
        Optional<NetworkDevice> existingNetworkDevice = networkDeviceRepository
                .findByIdAndHardwareName(networkDevice.getId(), networkDevice.getHardwareName());
    
        return existingNetworkDevice.orElseGet(() -> {
            NetworkDevice newNetworkDevice = new NetworkDevice();
            newNetworkDevice.setHardwareName(networkDevice.getHardwareName());
            newNetworkDevice.setManufacturer(networkDevice.getManufacturer());
            newNetworkDevice.setPurchaseDate(networkDevice.getPurchaseDate());
            newNetworkDevice.setSerialNumber(networkDevice.getSerialNumber());
            newNetworkDevice.setWarrantyEndDate(networkDevice.getWarrantyEndDate());
            newNetworkDevice.setLocation(networkDevice.getLocation());
            newNetworkDevice.setQuantity(networkDevice.getQuantity());
            newNetworkDevice.setCost(networkDevice.getCost());
            return networkDeviceRepository.save(newNetworkDevice);
        });
    }
    
}
