package com.first.capstone.services;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.first.capstone.dto.ResponseDTO;
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

    public List<Manufacturer> getAllManufacturersBysoftware() {
        return manufacturerRepository.findByFieldOfWorkHardware();
    }

    public List<Manufacturer> getAllManufacturersByhardware() {
        return manufacturerRepository.findByFieldOfWorkSoftware();
    }

    public ResponseEntity<Manufacturer> getManufacturerById(Long id) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(id);
        if (manufacturer.isPresent()) {
            return ResponseEntity.ok(manufacturer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<ResponseDTO> deleteManufacturerById(Long id) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(id);
        if (manufacturer.isPresent()) {
            manufacturerRepository.deleteById(id);
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody("Manufacturer deleted successfully");
            return ResponseEntity.ok(responseDTO);
        } else {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody("Manufacturer not found");
            return ResponseEntity.ok(responseDTO);

        }
    }

    public ResponseEntity<ResponseDTO> updateManufactureById(Long id, Manufacturer manufacturer) {
        Optional<Manufacturer> existingManufacturer = manufacturerRepository.findById(id);
        if (existingManufacturer.isPresent()) {
            Manufacturer updatedManufacturer = existingManufacturer.get();
            updatedManufacturer.setName(manufacturer.getName());
            updatedManufacturer.setFieldOfWork(manufacturer.getFieldOfWork());
            updatedManufacturer.setCompanyWebsiteLink(manufacturer.getCompanyWebsiteLink());
            updatedManufacturer.setEmailId(manufacturer.getEmailId());
            manufacturerRepository.save(updatedManufacturer);
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody("Manufacturer updated successfully");
            return ResponseEntity.ok(responseDTO);
        } else {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody("Manufacturer not found");
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }
    
    }

    


    
}
