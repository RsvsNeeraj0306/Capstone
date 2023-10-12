package com.first.capstone.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.first.capstone.Entity.DeviceType;
import com.first.capstone.Respositories.DeviceTypeRepository;

@Service
public class DeviceTypeService {

    private final DeviceTypeRepository deviceTypeRepository;

    @Autowired
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
