package com.first.capstone.services;

import com.first.capstone.entity.Manufacturer;
import com.first.capstone.respositories.ManufacturerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ManufacturerServiceTest {

    private ManufacturerService manufacturerService;

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        manufacturerService = new ManufacturerService(manufacturerRepository);
    }

    @Test
    void testManufacturerExists() {
        // Mock the behavior of the repository
        when(manufacturerRepository.existsByNameAndFieldOfWork("ManufacturerName", "FieldOfWork"))
            .thenReturn(true);

        // Call the service method
        boolean exists = manufacturerService.manufacturerExists("ManufacturerName", "FieldOfWork");

        // Assertion
        assertTrue(exists);
    }

    @Test
    void testSaveManufacturer() {
        // Create a Manufacturer object for testing
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("TestManufacturer");

        // Mock the behavior of the repository
        when(manufacturerRepository.save(manufacturer)).thenReturn(manufacturer);

        // Call the service method
        Manufacturer savedManufacturer = manufacturerService.saveManufacturer(manufacturer);

        // Assertion
        assertEquals(manufacturer, savedManufacturer);
    }

    @Test
    void testGetAllManufacturers() {
        // Create a list of manufacturers for testing
        List<Manufacturer> manufacturers = new ArrayList<>();
        manufacturers.add(createManufacturer(1L, "Manufacturer1", "FieldOfWork"));
        manufacturers.add(createManufacturer(2L, "Manufacturer2", "FieldOfWork"));

        // Mock the behavior of the repository
        when(manufacturerRepository.findAll()).thenReturn(manufacturers);

        // Call the service method
        List<Manufacturer> result = manufacturerService.getAllManufacturers();

        // Assertions
        assertEquals(2, result.size()); // Ensure the correct number of items
        assertEquals("Manufacturer1", result.get(0).getName());
        assertEquals("Manufacturer2", result.get(1).getName());
    }

    @Test
    void testGetOrCreateManufacturer_Existing() {
        // Create a Manufacturer object for testing
        Manufacturer manufacturer = createManufacturer(1L, "TestManufacturer", "TestField");

        // Mock the behavior of the repository
        when(manufacturerRepository.findByNameAndFieldOfWork("TestManufacturer", "TestField"))
            .thenReturn(Optional.of(manufacturer));

        // Call the service method
        Manufacturer result = manufacturerService.getOrCreateManufacturer(manufacturer);

        // Assertion
        assertEquals(manufacturer, result);
    }

    @Test
    void testGetOrCreateManufacturer_New() {
        // Create a Manufacturer object for testing
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("TestManufacturer");
        manufacturer.setFieldOfWork("TestField");

        // Mock the behavior of the repository
        when(manufacturerRepository.findByNameAndFieldOfWork("TestManufacturer", "TestField"))
            .thenReturn(Optional.empty());
        when(manufacturerRepository.save(any(Manufacturer.class))).thenReturn(manufacturer);

        // Call the service method
        Manufacturer result = manufacturerService.getOrCreateManufacturer(manufacturer);

        // Assertion
        assertNotNull(result);
        assertEquals("TestManufacturer", result.getName());
        assertEquals("TestField", result.getFieldOfWork());
        // Add more specific assertions based on your expected results
    }

    @Test
    void testGetAllManufacturersBysoftware() {
        // Create a list of manufacturers for testing
        List<Manufacturer> manufacturers = new ArrayList<>();
        manufacturers.add(createManufacturer(1L, "Manufacturer1", "Software"));
        manufacturers.add(createManufacturer(2L, "Manufacturer2", "Software"));

        // Mock the behavior of the repository
        when(manufacturerRepository.findByFieldOfWorkHardware()).thenReturn(manufacturers);

        // Call the service method
        List<Manufacturer> result = manufacturerService.getAllManufacturersBysoftware();

        // Assertions
        assertEquals(2, result.size()); // Ensure the correct number of items
        assertEquals("Manufacturer1", result.get(0).getName());
        assertEquals("Manufacturer2", result.get(1).getName());
    }

    @Test
    void testGetAllManufacturersByhardware() {
        // Create a list of manufacturers for testing
        List<Manufacturer> manufacturers = new ArrayList<>();
        manufacturers.add(createManufacturer(1L, "Manufacturer1", "Hardware"));
        manufacturers.add(createManufacturer(2L, "Manufacturer2", "Hardware"));

        // Mock the behavior of the repository
        when(manufacturerRepository.findByFieldOfWorkSoftware()).thenReturn(manufacturers);

        // Call the service method
        List<Manufacturer> result = manufacturerService.getAllManufacturersByhardware();

        // Assertions
        assertEquals(2, result.size()); // Ensure the correct number of items
        assertEquals("Manufacturer1", result.get(0).getName());
        assertEquals("Manufacturer2", result.get(1).getName());
    }


    private Manufacturer createManufacturer(Long id, String name, String fieldOfWork) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setFieldOfWork(fieldOfWork);
        return manufacturer;
    }

 
}
