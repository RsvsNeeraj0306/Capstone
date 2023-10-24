package com.first.capstone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.entity.Manufacturer;
import com.first.capstone.respositories.ManufacturerRepository;
import com.first.capstone.services.ManufacturerService;

@CrossOrigin()
@RestController
@RequestMapping("/api")
public class ManufactureController {

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @PostMapping("/addManufacturer")
    public ResponseEntity<Manufacturer> createManufacturer(@RequestBody Manufacturer manufacturer) {
        Manufacturer createdManufacturer = manufacturerService.getOrCreateManufacturer(manufacturer);
        return ResponseEntity.ok(createdManufacturer);
    }

    @GetMapping("/allManufacture")
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerService.getAllManufacturers();
    }

    @GetMapping("/allManufactureBySoftware")
    public List<Manufacturer> getAllManufacturersBySoftware() {
        return manufacturerService.getAllManufacturersBysoftware();
    }

    @GetMapping("/allManufactureByHardware")
    public List<Manufacturer> getAllManufacturersByHardware() {
        return manufacturerService.getAllManufacturersByhardware();
    }

    @GetMapping("/getManufacturerById/{id}")
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable(value = "id") Long id) {
        return manufacturerRepository.findById(id).map(manufacturer -> ResponseEntity.ok().body(manufacturer))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/updateManufacturer/{id}")
    public ResponseEntity<ResponseDTO> updateManufacturer(@PathVariable Long id, @RequestBody Manufacturer manufacturer) {
        return manufacturerService.updateManufactureById(id, manufacturer);
    }
    


}
