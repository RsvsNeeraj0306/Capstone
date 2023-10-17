package com.first.capstone.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.first.capstone.entity.Software;
import com.first.capstone.respositories.SoftwareRepository;

@Service
public class SoftwareService {

    private final SoftwareRepository softwareRepository;


    public SoftwareService(SoftwareRepository softwareRepository) {
        this.softwareRepository = softwareRepository;
    }

    public List<Software> findAllSoftware() {
        return softwareRepository.findAll();
    }

    public Software saveSoftware(Software software) {
        return softwareRepository.save(software);
    }

    // Implement other service methods as needed
}
