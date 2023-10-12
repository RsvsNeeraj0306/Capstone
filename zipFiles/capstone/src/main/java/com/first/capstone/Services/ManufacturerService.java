package com.first.capstone.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.first.capstone.DTO.ManufacturerDTO;
import com.first.capstone.Entity.Manufacturer;
import com.first.capstone.Respositories.ManufacturerRepository;
import com.first.capstone.dto.ManufacturerDTO;

@Service
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public boolean manufacturerExists(String name, String fieldOfWork) {
        return manufacturerRepository.existsByNameAndFieldOfWork(name, fieldOfWork);
    }

    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        // Implement the logic to save the manufacturer to the database.
        return manufacturerRepository.save(manufacturer);
    }

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Manufacturer getOrCreateManufacturer(Manufacturer manufacturer) {

    Optional<Manufacturer> existingManufacturer = manufacturerRepository.findByNameAndFieldOfWork(manufacturer.getName(), manufacturer.getFieldOfWork());

    return existingManufacturer.orElseGet(() -> {
        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setName(manufacturer.getName());
        newManufacturer.setFieldOfWork(manufacturer.getFieldOfWork());
        newManufacturer.setCompanyWebsiteLink(manufacturer.getCompanyWebsiteLink());
        newManufacturer.setEmailId(manufacturer.getEmailId());
        System.out.println(manufacturer+"before saving");
        return manufacturerRepository.save(newManufacturer);

    });
}

    
}
