package com.first.capstone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.first.capstone.entity.Manufacturer;
import com.first.capstone.services.ManufacturerService;

@CrossOrigin()
@RestController
@RequestMapping("/api")
public class ManufactureController {

    @Autowired
    private ManufacturerService manufacturerService;

    @PostMapping("/addManufacturer")
    public ResponseEntity<Manufacturer> createManufacturer(@RequestBody Manufacturer manufacturer) {
        Manufacturer createdManufacturer = manufacturerService.getOrCreateManufacturer(manufacturer);
        return ResponseEntity.ok(createdManufacturer);
    }

    @GetMapping("/allManufacture")
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerService.getAllManufacturers();
    }

    


}