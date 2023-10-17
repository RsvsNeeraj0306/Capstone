package com.first.capstone.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.first.capstone.entity.NetworkDevice;
import com.first.capstone.respositories.NetworkDeviceRepository;

import jakarta.transaction.Transactional;

@Service
public class NetworkDeviceService {

    private final NetworkDeviceRepository networkDeviceRepository;

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
    

    // Implement other service methods as needed
}
