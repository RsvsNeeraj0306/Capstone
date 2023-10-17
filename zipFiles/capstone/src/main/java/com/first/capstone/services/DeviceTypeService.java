package com.first.capstone.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.first.capstone.entity.DeviceType;
import com.first.capstone.respositories.DeviceTypeRepository;

@Service
public class DeviceTypeService {

    private final DeviceTypeRepository deviceTypeRepository;

    public DeviceTypeService(DeviceTypeRepository deviceTypeRepository) {
        this.deviceTypeRepository = deviceTypeRepository;
    }

    public List<DeviceType> findAllDeviceTypes() {
        return deviceTypeRepository.findAll();
    }

    public DeviceType saveDeviceType(DeviceType deviceType) {
        return deviceTypeRepository.save(deviceType);
    }

    // Implement other service methods as needed
}
