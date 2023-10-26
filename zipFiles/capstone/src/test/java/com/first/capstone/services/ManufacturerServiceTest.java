package com.first.capstone.services;

import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.entity.ManufactureHistory;
import com.first.capstone.entity.Manufacturer;
import com.first.capstone.respositories.ManufactureHistoryRepository;
import com.first.capstone.respositories.ManufacturerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class ManufacturerServiceTest {

    @InjectMocks
    private ManufacturerService manufacturerService;

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @Mock
    private ManufactureHistoryRepository manufacturerHistoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        manufacturerService = new ManufacturerService(manufacturerRepository, manufacturerHistoryRepository);
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
        Manufacturer manufacturer = createManufacturer(1L, "TestManufacturer", "TestField");

        // Mock the behavior of the repository
        when(manufacturerRepository.findByNameAndFieldOfWork("TestManufacturer", "TestField"))
                .thenReturn(Optional.empty());
        when(manufacturerRepository.save(any(Manufacturer.class))).thenReturn(manufacturer);

        // Call the service method
        Manufacturer result = manufacturerService.getOrCreateManufacturer(manufacturer);

        // Assertion
        assertEquals(manufacturer, result);
    }

    @Test
    void testGetManufacturerByIdExisting() {
        Long manufacturerId = 1L;
        Manufacturer expectedManufacturer = new Manufacturer();
        expectedManufacturer.setId(manufacturerId);
        expectedManufacturer.setName("ManufacturerName");

        // Mock the behavior of the manufacturerRepository
        Mockito.when(manufacturerRepository.findById(manufacturerId))
                .thenReturn(Optional.of(expectedManufacturer));

        ResponseEntity<Manufacturer> response = manufacturerService.getManufacturerById(manufacturerId);

        // Assert that the response is OK and matches the expected manufacturer
        assertEquals(ResponseEntity.ok(expectedManufacturer), response);
    }

    @Test
    void testGetManufacturerByIdNotFound() {
        Long manufacturerId = 2L;

        // Mock the behavior of the manufacturerRepository
        Mockito.when(manufacturerRepository.findById(manufacturerId))
                .thenReturn(Optional.empty());

        ResponseEntity<Manufacturer> response = manufacturerService.getManufacturerById(manufacturerId);

        // Assert that the response is Not Found
        assertEquals(ResponseEntity.notFound().build(), response);
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

@Test
 void testUpdateManufacturerByIdSuccess() {
    // Create an updated manufacturer
    Manufacturer updatedManufacturer = new Manufacturer();
    updatedManufacturer.setName("Updated Manufacturer");

    // Create an existing manufacturer
    Manufacturer existingManufacturer = new Manufacturer();
    existingManufacturer.setName("Existing Manufacturer");

    // Stub the repository to return the existing manufacturer
    when(manufacturerRepository.findById(1L)).thenReturn(Optional.of(existingManufacturer));

    // Invoke the service method
    ResponseEntity<ResponseDTO> response = manufacturerService.updateManufactureById(1L, updatedManufacturer);

    // Verify that the response indicates "ok"
    assertEquals(HttpStatus.OK, response.getStatusCode());

    // Verify that the manufacturerRepository's save method was called once
    verify(manufacturerRepository, times(1)).save(existingManufacturer);

    // Verify that the existing manufacturer's name was updated
    assertEquals("Updated Manufacturer", existingManufacturer.getName());
}

@Test
 void testUpdateManufacturerByIdNotFound() {
    // Create an updated manufacturer
    Manufacturer updatedManufacturer = new Manufacturer();
    updatedManufacturer.setName("Updated Manufacturer");

    // Stub the repository to return an empty Optional, simulating not finding a manufacturer
    when(manufacturerRepository.findById(1L)).thenReturn(Optional.empty());

    // Invoke the service method
    ResponseEntity<ResponseDTO> response = manufacturerService.updateManufactureById(1L, updatedManufacturer);

    // Verify that the response indicates "not found"
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    // Verify that the manufacturerRepository's save method was not called
    verify(manufacturerRepository, never()).save(updatedManufacturer);
}



    @Test
    void testDeleteSoftwareById() {
        // Create a Manufacturer object for testing
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(1L);
        manufacturer.setName("TestManufacturer");
        manufacturer.setFieldOfWork("Software");

        // Mock the behavior of the repository
        when(manufacturerRepository.findById(1L)).thenReturn(Optional.of(manufacturer));

        // Call the service method
        ResponseEntity<ResponseDTO> responseEntity = manufacturerService.deleteManufacturerById(1L);

        // Assertion
        assertEquals("Manufacturer deleted successfully", responseEntity.getBody().getResponseBody());
    }

    @Test
 void testDeleteManufacturerByIdNotFound() {
    // Stub the repository to return an empty Optional, simulating not finding a manufacturer
    when(manufacturerRepository.findById(1L)).thenReturn(Optional.empty());

    // Invoke the service method
    ResponseEntity<ResponseDTO> response = manufacturerService.deleteManufacturerById(1L);

    // Verify that the response is as expected
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Manufacturer not found", response.getBody().getResponseBody());

    // Verify that the manufacturerRepository's deleteById method was not called
    verify(manufacturerRepository, never()).deleteById(1L);
}

    @Test
    void testAddManufactureHistory() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Test Manufacturer");
        manufacturer.setFieldOfWork("Technology");
        manufacturer.setCompanyWebsiteLink("https://www.example.com");
        manufacturer.setEmailId("test@example.com");

        String action = "Added";

        ManufactureHistory manufacturerHistory = new ManufactureHistory();
        manufacturerHistory.setName(manufacturer.getName());
        manufacturerHistory.setFieldOfWork(manufacturer.getFieldOfWork());
        manufacturerHistory.setCompanyWebsiteLink(manufacturer.getCompanyWebsiteLink());
        manufacturerHistory.setEmailId(manufacturer.getEmailId());
        manufacturerHistory.setAction(action);

        Mockito.when(manufacturerHistoryRepository.save(Mockito.any(ManufactureHistory.class)))
                .thenReturn(manufacturerHistory);

        ResponseEntity<ResponseDTO> response = manufacturerService.addManufactureHistory(manufacturer, action);

        // Assert that the response is OK and contains the "added successfully" message
        assertEquals("Manufacturer history added successfully", response.getBody().getResponseBody());
    }

    private Manufacturer createManufacturer(Long id, String name, String fieldOfWork) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setFieldOfWork(fieldOfWork);
        return manufacturer;
    }

}
