package com.first.capstone.services;

import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.dto.SoftwareDeviceDTO;
import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.Software;
import com.first.capstone.entity.SoftwareAnalysis;
import com.first.capstone.entity.SoftwareLicenseHistory;

import com.first.capstone.respositories.SoftwareLicenseHistoryRepository;
import com.first.capstone.respositories.SoftwareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

class SoftwareServiceTest {

    @InjectMocks
    private SoftwareService softwareService;

    private ManufacturerService manufacturerService;

    @Mock
    private Manufacturer manufacturer;

    @Mock
    private SoftwareRepository softwareRepository;

    @Mock
    private SoftwareLicenseHistoryRepository softwareLicenseHistoryRepository;

    @Mock
    private Software software;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testFindAllSoftware() {
        // Create a list of Software objects for testing
        List<Software> softwareList = new ArrayList<>();
        softwareList.add(new Software());
        softwareList.add(new Software());

        // Mock the behavior of the repository
        when(softwareRepository.findAll()).thenReturn(softwareList);

        // Call the service method
        List<Software> result = softwareService.findAllSoftware();

        // Assertions
        assertEquals(2, result.size()); // Ensure the correct number of items
    }

    @Test
    void testSaveSoftware() {
        // Create a Software object for testing
        Software software = new Software();

        // Mock the behavior of the repository
        when(softwareRepository.save(software)).thenReturn(software);

        // Call the service method to save the Software
        Software savedSoftware = softwareService.saveSoftware(software);

        // Assertions
        assertEquals(software, savedSoftware);
    }

    @Test
     void testGetOrCreateNewSoftware_ExistingSoftware() {
        // Create a sample manufacturer and software
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(1L);

        Software software = new Software();
        software.setSoftwareName("Existing Software");

        // Create a mock response for the repository
        Software existingSoftware = new Software();
        existingSoftware.setId(1L);
        when(softwareRepository.findByIdAndSoftwareName(manufacturer.getId(), software.getSoftwareName()))
            .thenReturn(java.util.Optional.of(existingSoftware));

        // Test the method
        Software result = softwareService.getOrCreateNewSoftware(software, manufacturer);

        // Assert that the result is the existing software
        assertEquals(existingSoftware, result);
    }

    @Test
     void testGetOrCreateNewSoftware_NewSoftware() {
        // Create a sample manufacturer and software
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(2L);

        Software software = new Software();
        software.setSoftwareName("New Software");

        // Mock the repository to return an empty Optional
        when(softwareRepository.findByIdAndSoftwareName(manufacturer.getId(), software.getSoftwareName()))
            .thenReturn(java.util.Optional.empty());

        // Mock the repository's save method to return the newly created software
        Software newSoftware = new Software();
        when(softwareRepository.save(any(Software.class))).thenReturn(newSoftware);

        // Test the method
        Software result = softwareService.getOrCreateNewSoftware(software, manufacturer);

        // Assert that the result is the new software
        assertEquals(newSoftware, result);
    }

   
    @Test
    void testAddSoftware() {
        // Create a SoftwareDeviceDTO with required data
        SoftwareDeviceDTO softwareDeviceDTO = new SoftwareDeviceDTO();
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(1L);
        softwareDeviceDTO.setManufacturer(manufacturer);
    
        manufacturerService = mock(ManufacturerService.class);
        softwareRepository = mock(SoftwareRepository.class); // Mock the SoftwareRepository
        softwareLicenseHistoryRepository = mock(SoftwareLicenseHistoryRepository.class); // Mock the SoftwareLicenseHistoryRepository
        
        softwareService = new SoftwareService(softwareRepository, manufacturerService, softwareLicenseHistoryRepository);
    
        Software software = new Software();
        software.setSoftwareName("Test Software");
        software.setExpiryDate(Date.valueOf(LocalDate.now().plusDays(30)));
        
        when(manufacturerService.getOrCreateManufacturer(manufacturer)).thenReturn(manufacturer);
        when(softwareRepository.save(any(Software.class))).thenReturn(software); // Mock save method to return software
        when(softwareLicenseHistoryRepository.save(any(SoftwareLicenseHistory.class))).thenReturn(null);
    
        softwareDeviceDTO.setSoftware(software);
    
        SoftwareLicenseHistory softwareLicenseHistory = new SoftwareLicenseHistory();
        softwareLicenseHistory.setSoftware(software);
        softwareLicenseHistory.setLicenseKey("123-456-789");
        softwareLicenseHistory.setExpiryDate(Date.valueOf(LocalDate.now().plusDays(30)));
        softwareLicenseHistory.setPurchaseDate(Date.valueOf(LocalDate.now()));
        softwareDeviceDTO.setSoftwareLicenseHistory(softwareLicenseHistory);
    
        SoftwareAnalysis softwareAnalysis = new SoftwareAnalysis();
        softwareAnalysis.setSoftware(software);
        softwareDeviceDTO.setSoftwareAnalysis(softwareAnalysis);
    
        // Call the addSoftware method
        ResponseEntity<ResponseDTO> responseEntity = softwareService.addSoftware(softwareDeviceDTO);
    
        // Assertions
        verify(manufacturerService, times(1)).getOrCreateManufacturer(manufacturer); // Ensure it's called once
        verify(softwareRepository, times(1)).save(any(Software.class)); // Ensure save method is called once
        verify(softwareLicenseHistoryRepository, times(1)).save(any(SoftwareLicenseHistory.class)); // Ensure save method is called once
       
    
        assertEquals("Software added successfully", responseEntity.getBody().getResponseBody());
    }
    
    

    @Test
    void testAddLicenseHistory_ExistingSoftware() {
        // Create a SoftwareDeviceDTO with required data
        SoftwareDeviceDTO softwareDeviceDTO = new SoftwareDeviceDTO();
        Software software = new Software();
        software.setId(1L); // Assuming an existing software with ID 1
        softwareDeviceDTO.setSoftware(software);
        SoftwareLicenseHistory licenseHistory = new SoftwareLicenseHistory();
        licenseHistory.setLicenseKey("TestLicenseKey");
        softwareDeviceDTO.setSoftwareLicenseHistory(licenseHistory);

        // Mock the behavior of softwareRepository to return the existing software
        when(softwareRepository.findById(1L)).thenReturn(Optional.of(software));

        // Call the addLicenseHistory method
        ResponseEntity<ResponseDTO> responseEntity = softwareService.addLicenseHistory(softwareDeviceDTO);

        softwareLicenseHistoryRepository.save(licenseHistory);

        // Assertions
        verify(softwareRepository, times(1)).findById(1L);
        verify(softwareRepository, times(1)).save(software);
        verify(softwareLicenseHistoryRepository, times(1)).save(licenseHistory);

        assertEquals("License history added successfully", responseEntity.getBody().getResponseBody());
    }

    @Test
    void testAddLicenseHistory_ExistingSoftwareNoLicenseHistory() {
        // Create a SoftwareDeviceDTO with required data
        SoftwareDeviceDTO softwareDeviceDTO = new SoftwareDeviceDTO();
        Software software = new Software();
        software.setId(1L); // Assuming an existing software with ID 1
        softwareDeviceDTO.setSoftware(software);
        SoftwareLicenseHistory licenseHistory = new SoftwareLicenseHistory();
        licenseHistory.setId(1L);
        licenseHistory.setSoftware(software);
        licenseHistory.setLicenseKey("TestLicenseKey");
        softwareDeviceDTO.setSoftwareLicenseHistory(licenseHistory);

        softwareLicenseHistoryRepository = mock(SoftwareLicenseHistoryRepository.class);

        // Mock the behavior of softwareRepository to return the existing software
        when(softwareRepository.findById(1L)).thenReturn(Optional.of(software));

     
        // Call the addLicenseHistory method
        ResponseEntity<ResponseDTO> responseEntity = softwareService.addLicenseHistory(softwareDeviceDTO);

        // Assertions
        verify(softwareRepository, times(1)).findById(1L);
        verify(softwareRepository, times(1)).save(software);

        assertEquals("License history added successfully", responseEntity.getBody().getResponseBody());
    }

    @Test
    void testAddLicenseHistory_NonExistingSoftware() {
        // Create a SoftwareDeviceDTO with non-existing software
        SoftwareDeviceDTO softwareDeviceDTO = new SoftwareDeviceDTO();
        Software software = new Software();
        software.setId(1L); // Assuming a non-existing software with ID 1
        softwareDeviceDTO.setSoftware(software);

        // Mock the behavior of softwareRepository to return no existing software
        when(softwareRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the addLicenseHistory method
        ResponseEntity<ResponseDTO> responseEntity = softwareService.addLicenseHistory(softwareDeviceDTO);

        // Assertions
        verify(softwareRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(softwareRepository);

        assertEquals("Software not found", responseEntity.getBody().getResponseBody());
    }

    @Test
    void testGetLicenseCounts() {
        // Create a list of Software objects for testing
        List<Software> licenses = new ArrayList<>();
        // Add licenses with different expiry dates for testing
        LocalDate today = LocalDate.now();
        licenses.add(createSoftware(today.minusDays(10))); // Expiring in the past (less than 45 days)
        licenses.add(createSoftware(today.plusDays(50))); // Expiring in the future (more than 45 days)
        licenses.add(createSoftware(today.plusDays(20))); // Expiring within 45 days
        licenses.add(createSoftware(today.minusDays(60))); // Expired (less than 0 days)

        // Mock the behavior of the softwareRepository
        when(softwareRepository.findAll()).thenReturn(licenses);

        // Call the service method
        ResponseEntity<Map<String, Long>> response = softwareService.getLicenseCounts();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Long> counts = response.getBody();
        assertNotNull(counts);
        assertEquals(4, counts.get("totalLicenses"));
        assertEquals(0, counts.get("licensesLessThanZero"));
        assertEquals(4, counts.get("licensesGreaterThan45"));
        assertEquals(0, counts.get("licensesLessThan45Count"));
    }

    private Software createSoftware(LocalDate expiryDate) {
        Software software = new Software();
        Date d = new Date(2023, 1, 1);
        software.setExpiryDate(d);
        return software;
    }

}
