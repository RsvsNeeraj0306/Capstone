package com.first.capstone.services;

import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.dto.SoftwareDeviceDTO;
import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.Software;
import com.first.capstone.entity.SoftwareLicenseHistory;
import com.first.capstone.respositories.SoftwareLicenseHistoryRepository;
import com.first.capstone.respositories.SoftwareRMARepository;
import com.first.capstone.respositories.SoftwareRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @Mock
    private SoftwareRMARepository softwareRMARepository;

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
        softwareService = new SoftwareService(softwareRepository, manufacturerService, softwareLicenseHistoryRepository,
                softwareRMARepository);

        Software software = new Software();
        software.setSoftwareName("Test Software");
        software.setExpiryDate(Date.valueOf(LocalDate.now().plusDays(30)));
        software.setLicenseKey("123-456-789");

        when(manufacturerService.getOrCreateManufacturer(manufacturer)).thenReturn(manufacturer);
        when(softwareRepository.save(any(Software.class))).thenReturn(software);
        softwareDeviceDTO.setSoftware(software);

        // Call the addSoftware method
        ResponseEntity<Software> responseEntity = softwareService.addSoftware(softwareDeviceDTO);

        // Assertions
        verify(manufacturerService, times(1)).getOrCreateManufacturer(manufacturer); // Ensure it's called once
        verify(softwareRepository, times(1)).save(any(Software.class)); // Ensure save method is called once
        verify(softwareLicenseHistoryRepository, times(1)).save(any(SoftwareLicenseHistory.class)); // Ensure save
            
        assertEquals(software, responseEntity.getBody()); // Ensure the correct response body
    }

    @Test
    void testAddLicenseHistory() {
        // Create a sample Software instance for testing
        Software software = new Software();
        software.setSoftwareName("Sample Software");
        software.setLicenseKey("Sample License Key");
        // Set other properties as needed for testing

        // Mock the behavior of your softwareLicenseHistoryRepository
        Mockito.when(softwareLicenseHistoryRepository.save(Mockito.any(SoftwareLicenseHistory.class)))
                .thenReturn(new SoftwareLicenseHistory()); // Mock the save operation

        // Call the method you want to test
        ResponseEntity<ResponseDTO> responseEntity = softwareService.addLicenseHistory(software, "ADDED");

        // Verify the expected behavior
        assertEquals("License history added successfully", responseEntity.getBody().getResponseBody());
    }

    @Test
    void testRenewSoftware() {
        // Create a sample SoftwareDeviceDTO for testing
        SoftwareDeviceDTO softwareDeviceDTO = new SoftwareDeviceDTO();
        Software software = new Software();
        software.setId(1L); // Set the ID for testing
        software.setSoftwareName("Test Software");
        softwareDeviceDTO.setSoftware(software);
        // Set other properties as needed for testing

        // Mock the behavior of your softwareRepository and
        // softwareLicenseHistoryRepository
        Optional<Software> existingSoftware = Optional.of(new Software());
        Mockito.when(softwareRepository.findById(1L)).thenReturn(existingSoftware);
        Mockito.when(softwareRepository.save(Mockito.any(Software.class)))
                .thenReturn(existingSoftware.get()); // Mock the save operation

        // Call the method you want to test
        ResponseEntity<ResponseDTO> responseEntity = softwareService.renewSoftware(softwareDeviceDTO);

        // Verify the expected behavior

        assertEquals("Software renewed successfully", responseEntity.getBody().getResponseBody());
    }

    @Test
    void testRenewSoftware_NotFound() {
        // Create a sample SoftwareDeviceDTO for testing
        SoftwareDeviceDTO softwareDeviceDTO = new SoftwareDeviceDTO();
        Software software = new Software();
        software.setId(1L); // Set the ID for testing
        software.setSoftwareName("Test Software");
        softwareDeviceDTO.setSoftware(software);
        // Set other properties as needed for testing

        // Mock the behavior of your softwareRepository and
        // softwareLicenseHistoryRepository
        Mockito.when(softwareRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the method you want to test
        ResponseEntity<ResponseDTO> responseEntity = softwareService.renewSoftware(softwareDeviceDTO);

        // Verify the expected behavior

        assertEquals("Software not found", responseEntity.getBody().getResponseBody());
    }

    @Test
    void testChangePlan() {
        // Create a sample SoftwareDeviceDTO for testing
        SoftwareDeviceDTO softwareDeviceDTO = new SoftwareDeviceDTO();
        Software software = new Software();
        software.setId(1L); // Set the ID for testing
        software.setSoftwareName("Test Software");
        softwareDeviceDTO.setSoftware(software);
        // Set other properties as needed for testing

        // Mock the behavior of your softwareRepository
        Optional<Software> existingSoftware = Optional.of(new Software());
        Mockito.when(softwareRepository.findById(1L)).thenReturn(existingSoftware);
        Mockito.when(softwareRepository.save(Mockito.any(Software.class)))
                .thenReturn(existingSoftware.get()); // Mock the save operation

        // Call the method you want to test
        ResponseEntity<ResponseDTO> responseEntity = softwareService.changePlan(softwareDeviceDTO);

        // Verify the expected behavior
        assertEquals("Software Plan changed successfully", responseEntity.getBody().getResponseBody());
    }

    @Test
    void testChangePlan_NotFound() {
        // Create a sample SoftwareDeviceDTO for testing
        SoftwareDeviceDTO softwareDeviceDTO = new SoftwareDeviceDTO();
        Software software = new Software();
        software.setId(1L); // Set the ID for testing
        software.setSoftwareName("Test Software");
        softwareDeviceDTO.setSoftware(software);
        // Set other properties as needed for testing

        // Mock the behavior of your softwareRepository
        Mockito.when(softwareRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the method you want to test
        ResponseEntity<ResponseDTO> responseEntity = softwareService.changePlan(softwareDeviceDTO);

        // Verify the expected behavior
        assertEquals("Software not found", responseEntity.getBody().getResponseBody());
    }

    @Test
     void testRefundSoftware_ExistingSoftware() {
        // Create a sample Software object
        Software software = new Software();
        software.setId(1L); // Assuming this is the ID of an existing software

        // Mock the behavior of the softwareRepository to return the sample software
        Mockito.when(softwareRepository.findById(software.getId())).thenReturn(Optional.of(software));

        // Call the method you want to test
        SoftwareDeviceDTO softwareDeviceDTO = new SoftwareDeviceDTO();
        softwareDeviceDTO.setSoftware(software);
        ResponseEntity<ResponseDTO> responseEntity = softwareService.refundSoftware(softwareDeviceDTO);

      
        // You can also verify other aspects of the response, such as the response body and content.
        // For example, check that the response body contains the expected message.
        assertEquals("Software refunded successfully", responseEntity.getBody().getResponseBody());
    }

    @Test
     void testRefundSoftware_NonExistingSoftware() {
        // Create a sample Software object
        Software software = new Software();
        software.setId(1L); // Assuming this is the ID of a non-existing software

        // Mock the behavior of the softwareRepository to return an empty Optional (indicating non-existence)
        Mockito.when(softwareRepository.findById(software.getId())).thenReturn(Optional.empty());

        // Call the method you want to test
        SoftwareDeviceDTO softwareDeviceDTO = new SoftwareDeviceDTO();
        softwareDeviceDTO.setSoftware(software);
        ResponseEntity<ResponseDTO> responseEntity = softwareService.refundSoftware(softwareDeviceDTO);

       
        // You can also verify other aspects of the response, such as the response body and content.
        // For example, check that the response body contains the expected error message.
        assertEquals("Software not found", responseEntity.getBody().getResponseBody());
    }

    @Test
     void testSetSoftwareAnalysis_ExistingSoftware() {
        // Create a sample Software object
        Software software = new Software();
        software.setId(1L); // Assuming this is the ID of an existing software

        // Mock the behavior of the softwareRepository to return the sample software
        Mockito.when(softwareRepository.findById(software.getId())).thenReturn(Optional.of(software));

        // Call the method you want to test
        SoftwareDeviceDTO softwareDeviceDTO = new SoftwareDeviceDTO();
        softwareDeviceDTO.setSoftware(software);
        ResponseEntity<ResponseDTO> responseEntity = softwareService.setSoftwareAnalysis(softwareDeviceDTO);

        

        // You can also verify other aspects of the response, such as the response body and content.
        // For example, check that the response body contains the expected message.
        assertEquals("Software Analysis added successfully", responseEntity.getBody().getResponseBody());
    }

    @Test
     void testSetSoftwareAnalysis_NonExistingSoftware() {
        // Create a sample Software object
        Software software = new Software();
        software.setId(1L); // Assuming this is the ID of a non-existing software

        // Mock the behavior of the softwareRepository to return an empty Optional (indicating non-existence)
        Mockito.when(softwareRepository.findById(software.getId())).thenReturn(Optional.empty());

        // Call the method you want to test
        SoftwareDeviceDTO softwareDeviceDTO = new SoftwareDeviceDTO();
        softwareDeviceDTO.setSoftware(software);
        ResponseEntity<ResponseDTO> responseEntity = softwareService.setSoftwareAnalysis(softwareDeviceDTO);



        // You can also verify other aspects of the response, such as the response body and content.
        // For example, check that the response body contains the expected error message.
        assertEquals("Software not found", responseEntity.getBody().getResponseBody());
    }

    @Test
    void testDeleteSoftwareById() {
        // Create a sample software ID for testing
        Long softwareId = 1L;

        // Mock the behavior of your softwareRepository
        Optional<Software> existingSoftware = Optional.of(new Software());
        Mockito.when(softwareRepository.findById(softwareId)).thenReturn(existingSoftware);
        Mockito.doNothing().when(softwareRepository).delete(existingSoftware.get());

        // Call the method you want to test
        ResponseEntity<ResponseDTO> responseEntity = softwareService.deleteSoftwareById(softwareId);

        // Verify the expected behavior

        assertEquals("Software deleted successfully", responseEntity.getBody().getResponseBody());
    }

    @Test
    void testDeleteSoftwareById_NonExistingSoftware() {
        // Create a sample software ID for testing
        Long softwareId = 1L;

        // Mock the behavior of your softwareRepository
        Mockito.when(softwareRepository.findById(softwareId)).thenReturn(Optional.empty());

        // Call the method you want to test
        ResponseEntity<ResponseDTO> responseEntity = softwareService.deleteSoftwareById(softwareId);

        // Verify the expected behavior

        assertEquals("Software not found", responseEntity.getBody().getResponseBody());
    }

    @Test
    void testGetSoftwareLessThan45days_WithData() {
        // Create a list of sample Software objects with different expiry dates
        List<Software> sampleSoftwareList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        sampleSoftwareList.add(createSoftware(today.minusDays(10))); // Expiring in the past (less than 45 days)
        sampleSoftwareList.add(createSoftware(today.plusDays(50))); // Expiring in the future (more than 45 days)
        sampleSoftwareList.add(createSoftware(today.plusDays(20))); // Expiring within 45 days

        // Mock the behavior of the softwareRepository to return the sample list
        Mockito.when(softwareRepository.findAll()).thenReturn(sampleSoftwareList);

        // Call the method you want to test
        ResponseEntity<List<Software>> responseEntity = softwareService.getSoftwareLessThan45days();
        // Verify the expected behavior
        assertEquals(1, responseEntity.getBody().size()); // Only one software object with expiry date within 45 days
    }

    @Test
    void testGetSoftwareLessThan45days_NoData() {
        // Create an empty list of Software objects
        List<Software> emptySoftwareList = new ArrayList<>();

        // Mock the behavior of the softwareRepository to return an empty list
        Mockito.when(softwareRepository.findAll()).thenReturn(emptySoftwareList);

        // Call the method you want to test
        ResponseEntity<List<Software>> responseEntity = softwareService.getSoftwareLessThan45days();

        // Verify the expected behavior when there's no data

        assertEquals(0, responseEntity.getBody().size()); // No software objects with expiry dates within 45 days
    }

    @Test
    void testGetSoftwareLessThanZerodays_withData(){
        // Create a list of sample Software objects with different expiry dates
        List<Software> sampleSoftwareList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        sampleSoftwareList.add(createSoftware(today.minusDays(10))); // Expiring in the past (less than 45 days)
        sampleSoftwareList.add(createSoftware(today.plusDays(50))); // Expiring in the future (more than 45 days)
        sampleSoftwareList.add(createSoftware(today.plusDays(20))); // Expiring within 45 days
        sampleSoftwareList.add(createSoftware(today.minusDays(60))); // Expired (less than 0 days)

        // Mock the behavior of the softwareRepository to return the sample list
        Mockito.when(softwareRepository.findAll()).thenReturn(sampleSoftwareList);

        // Call the method you want to test
        ResponseEntity<List<Software>> responseEntity = softwareService.getSoftwareLessThanZeroDays();
        // Verify the expected behavior
        assertEquals(2, responseEntity.getBody().size()); // Only one software object with expiry date within 45 days
    }


    @Test
    void testGetSoftwareLessThanZerodays_NoData(){
        // Create an empty list of Software objects
        List<Software> emptySoftwareList = new ArrayList<>();

        // Mock the behavior of the softwareRepository to return an empty list
        Mockito.when(softwareRepository.findAll()).thenReturn(emptySoftwareList);

        // Call the method you want to test
        ResponseEntity<List<Software>> responseEntity = softwareService.getSoftwareLessThanZeroDays();

        // Verify the expected behavior when there's no data

        assertEquals(0, responseEntity.getBody().size()); // No software objects with expiry dates within 45 days
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
        assertEquals(2, counts.get("licensesLessThanZero"));
        assertEquals(1, counts.get("licensesGreaterThan45"));
        assertEquals(1, counts.get("licensesLessThan45Count"));
    }

    private Software createSoftware(LocalDate expiryDate) {
        Software software = new Software();
        software.setExpiryDate(Date.valueOf(expiryDate));
        return software;
    }

}
