package com.first.capstone.services;

import com.first.capstone.controller.SoftwareController;
import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.dto.SoftwareDeviceDTO;
import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.Software;
import com.first.capstone.entity.SoftwareLicenseHistory;
import com.first.capstone.respositories.ManufacturerRepository;
import com.first.capstone.respositories.SoftwareLicenseHistoryRepository;
import com.first.capstone.respositories.SoftwareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    void testGetOrCreateNewSoftwareExisting() {
        // Create a Software object for testing
        Software software = new Software();
        software.setId(1L);
        software.setSoftwareName("TestSoftware");

        // Mock the behavior of the repository for an existing software
        when(softwareRepository.findByIdAndSoftwareName(1L, "TestSoftware"))
                .thenReturn(Optional.of(software));

        // Perform the test
        Software result = softwareService.getOrCreateNewSoftware(software);

        // Assertions
        assertEquals(1L, result.getId()); // Ensure the existing software is returned
    }

    @Test
    void testGetOrCreateNewSoftwareNonExisting() {
        // Create a Software object for testing
        Software software = new Software();
        software.setId(1L);
        software.setSoftwareName("TestSoftware");

        // Mock the behavior of the repository for a non-existing software
        when(softwareRepository.findByIdAndSoftwareName(1L, "TestSoftware"))
                .thenReturn(Optional.empty());

        // Mock the behavior of softwareRepository when saving the new software
        when(softwareRepository.save(any(Software.class))).thenAnswer(invocation -> {
            Software savedSoftware = invocation.getArgument(0);
            savedSoftware.setId(2L); // Simulate that the software gets an ID when saved
            return savedSoftware;
        });

        // Perform the test
        Software result = softwareService.getOrCreateNewSoftware(software);

        // Assertions
        assertEquals(2L, result.getId()); // Ensure a new software is created and returned with a different ID
    }

    /**
     * 
     */
    @Test
    void testAddSoftware() {
        // Create a SoftwareDeviceDTO with required data
        SoftwareDeviceDTO softwareDeviceDTO = new SoftwareDeviceDTO();
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(1L);
        softwareDeviceDTO.setManufacturer(manufacturer);

        manufacturerService = mock(ManufacturerService.class);
        softwareService = new SoftwareService(softwareRepository, manufacturerService, softwareLicenseHistoryRepository);


        Software software = new Software();
        software.setSoftwareName("Test Software");
        softwareDeviceDTO.setSoftware(software);

        SoftwareLicenseHistory softwareLicenseHistory = new SoftwareLicenseHistory();
        softwareLicenseHistory.setLicenseKey("123-456-789");
        softwareDeviceDTO.setSoftwareLicenseHistory(softwareLicenseHistory);

        // Mock the behavior of the manufacturer service to return the manufacturer
        when(manufacturerService.getOrCreateManufacturer(manufacturer)).thenReturn(manufacturer);

        // Mock the behavior of the softwareRepository and softwareLicenseHistoryRepository to return the saved objects
        when(softwareRepository.save(software)).thenReturn(software);
        when(softwareLicenseHistoryRepository.save(softwareLicenseHistory)).thenReturn(softwareLicenseHistory);

        // Call the addSoftware method
        ResponseEntity<ResponseDTO> responseEntity = softwareService.addSoftware(softwareDeviceDTO);

        // Assertions
        verify(manufacturerService, times(1)).getOrCreateManufacturer(manufacturer); // Ensure it's called once
        verifyNoMoreInteractions(manufacturerService); // Ensure no more interactions

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
    void testAddSoftware_SoftwareNotFound() {
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

        assertEquals("Software not found", responseEntity.getBody().getResponseBody());
    
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
        licenses.add(createSoftware(today.minusDays(10)));  // Expiring in the past (less than 45 days)
        licenses.add(createSoftware(today.plusDays(50)));   // Expiring in the future (more than 45 days)
        licenses.add(createSoftware(today.plusDays(20)));   // Expiring within 45 days
        licenses.add(createSoftware(today.minusDays(60)));  // Expired (less than 0 days)

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
