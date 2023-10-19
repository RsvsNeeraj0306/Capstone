package com.first.capstone.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.first.capstone.entity.Manufacturer;
import com.first.capstone.respositories.ManufacturerRepository;

@Service
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public boolean manufacturerExists(String name, String fieldOfWork) {
        return manufacturerRepository.existsByNameAndFieldOfWork(name, fieldOfWork);
    }

    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Manufacturer getOrCreateManufacturer(Manufacturer manufacturer) {

        Optional<Manufacturer> existingManufacturer = manufacturerRepository
                .findByNameAndFieldOfWork(manufacturer.getName(), manufacturer.getFieldOfWork());

        return existingManufacturer.orElseGet(() -> {
            Manufacturer newManufacturer = new Manufacturer();
            newManufacturer.setName(manufacturer.getName());
            newManufacturer.setFieldOfWork(manufacturer.getFieldOfWork());
            newManufacturer.setCompanyWebsiteLink(manufacturer.getCompanyWebsiteLink());
            newManufacturer.setEmailId(manufacturer.getEmailId());
            return manufacturerRepository.save(newManufacturer);

        });



    }

    

}
