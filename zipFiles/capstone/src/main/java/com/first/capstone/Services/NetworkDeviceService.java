package com.first.capstone.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.first.capstone.Entity.NetworkDevice;
import com.first.capstone.Respositories.NetworkDeviceRepository;

import jakarta.transaction.Transactional;

@Service
public class NetworkDeviceService {

    private final NetworkDeviceRepository networkDeviceRepository;

    @Autowired
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
