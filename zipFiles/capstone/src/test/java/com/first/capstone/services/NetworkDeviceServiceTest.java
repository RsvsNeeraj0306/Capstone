package com.first.capstone.services;

import com.first.capstone.dto.NetworkDeviceDTO;
import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.NetworkDevice;
import com.first.capstone.entity.NetworkDeviceAnalysis;
import com.first.capstone.entity.NetworkDeviceRMA;
import com.first.capstone.entity.NetworkDevicesHistory;
import com.first.capstone.respositories.NetworkDeviceAnalysisRepository;
import com.first.capstone.respositories.NetworkDeviceRMARepository;
import com.first.capstone.respositories.NetworkDeviceRepository;
import com.first.capstone.respositories.NetworkDevicesHistoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class NetworkDeviceServiceTest {

    @InjectMocks
    private NetworkDeviceService networkDeviceService;

    private ManufacturerService manufacturerService;

    @Mock
    private Manufacturer manufacturer;

    @Mock
    private NetworkDeviceRepository networkDeviceRepository;

    @Mock
    private NetworkDevicesHistoryRepository networkDeviceHistoryRepository;

    @Mock
    private NetworkDeviceAnalysis networkDeviceAnalysis;

    @Mock
    private NetworkDeviceRMA networkDeviceRMA;

    @Mock
    private NetworkDevice networkDevice;

    @Mock
    private NetworkDeviceAnalysisRepository networkDeviceAnalysisRepository;

    @Mock
    private NetworkDeviceRMARepository networkDeviceRMARepository;

    private final String ERROR_MESSAGE = "Network device not found";

   

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllNetworkDevices() {
        // Create a list of NetworkDevice objects for testing
        List<NetworkDevice> networkDevices = new ArrayList<>();
        networkDevices.add(new NetworkDevice());
        networkDevices.add(new NetworkDevice());

        // Mock the behavior of the networkDeviceRepository
        Mockito.when(networkDeviceRepository.findAll()).thenReturn(networkDevices);

        // Perform the test
        List<NetworkDevice> result = networkDeviceService.findAllNetworkDevices();

        // Assertions
        assertEquals(result.size(), networkDevices.size());
    }

    @Test
    void testSaveNetworkDevice() {
        // Create a NetworkDevice object for testing
        NetworkDevice networkDevice = new NetworkDevice();

        // Mock the behavior of the networkDeviceRepository
        Mockito.when(networkDeviceRepository.save(networkDevice)).thenReturn(networkDevice);

        // Perform the test
        NetworkDevice result = networkDeviceService.saveNetworkDevice(networkDevice);

        // Assertions
        assertNotNull(result);
    }

    @Test
    void testGetOrCreateNetworkDeviceExisting() {
        // Create a NetworkDevice object for testing
        NetworkDevice networkDevice = new NetworkDevice();
        networkDevice.setId(1L);
        networkDevice.setHardwareName("TestDevice");

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("TestManufacturer");
        manufacturer.setFieldOfWork("TestField");

        NetworkDevice existingNetworkDevice = new NetworkDevice();
        existingNetworkDevice.setId(1L);
        // Mock the behavior of the networkDeviceRepository for an existing device
        when(networkDeviceRepository.findByIdAndHardwareName(manufacturer.getId(), networkDevice.getHardwareName() ))
                .thenReturn(Optional.of(existingNetworkDevice));

        // Perform the test
        NetworkDevice result = networkDeviceService.getOrCreaNetworkDevice(networkDevice, manufacturer);

        // Assertions

    
        assertEquals(existingNetworkDevice, result);
    }

    @Test
    void testGetOrCreateNetworkDeviceCreateNew() {
        // Create a NetworkDevice object for testing
        NetworkDevice networkDevice = new NetworkDevice();
        networkDevice.setId(1L);
        networkDevice.setHardwareName("TestDevice");

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("TestManufacturer");
        manufacturer.setFieldOfWork("TestField");

        // Mock the behavior of the networkDeviceRepository to return an empty Optional
        Mockito.when(networkDeviceRepository.findByIdAndHardwareName(1L, "TestDevice"))
                .thenReturn(Optional.empty());

        // Mock the behavior of networkDeviceRepository when saving the new device
        Mockito.when(networkDeviceRepository.save(Mockito.any(NetworkDevice.class))).thenAnswer(invocation -> {
            NetworkDevice savedDevice = invocation.getArgument(0);
            savedDevice.setId(2L); // Simulate that the device gets an ID when saved
            return savedDevice;
        });

        // Perform the test
        NetworkDevice result = networkDeviceService.getOrCreaNetworkDevice(networkDevice, manufacturer);

        // Assertions
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotEquals(1L, result.getId()); // The ID should be different after saving
        assertEquals("TestDevice", result.getHardwareName());
    }


     @Test
    void testAddNetworkDevice() {
        // Create a sample NetworkDeviceDTO with a networkDevice
        NetworkDeviceDTO networkDeviceDTO = new NetworkDeviceDTO();
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(1L);
        networkDeviceDTO.setManufacturer(manufacturer);
    
        manufacturerService = mock(ManufacturerService.class);
        networkDeviceRepository = mock(NetworkDeviceRepository.class);
        networkDeviceHistoryRepository = mock(NetworkDevicesHistoryRepository.class);
    
        networkDeviceService = new NetworkDeviceService(networkDeviceRepository, manufacturerService, networkDeviceHistoryRepository, networkDeviceAnalysisRepository, networkDeviceRMARepository);
    
        NetworkDevice networkDevice = new NetworkDevice();
        networkDevice.setId(1L);
        networkDevice.setHardwareName("Test Hardware");
        networkDevice.setWarrantyEndDate(Date.valueOf(LocalDate.now().plusYears(1)));
        networkDevice.setPurchaseDate(Date.valueOf(LocalDate.now()));
    
        // Set the networkDevice in the NetworkDeviceDTO
        networkDeviceDTO.setNetworkDevice(networkDevice);
    
        when(manufacturerService.getOrCreateManufacturer(manufacturer)).thenReturn(manufacturer);
        when(networkDeviceRepository.save(networkDevice)).thenReturn(networkDevice);
// Mock the behavior of networkDeviceRepository when saving the new device
        // Mock the behavior of networkDeviceRepository when saving the new device
        when(networkDeviceRepository.save(Mockito.any(NetworkDevice.class))).thenReturn(networkDevice);


        // Perform the test
        ResponseEntity<ResponseDTO> responseEntity = networkDeviceService.addNetworkDevice(networkDeviceDTO);
    
        assertEquals("Network device added successfully", responseEntity.getBody().getResponseBody());
    }

    @Test
    void testSetNetworkDeviceAnalysisSuccess() {
        NetworkDeviceDTO networkDeviceDTO = new NetworkDeviceDTO();
        NetworkDevice networkDevice = new NetworkDevice();
        networkDevice.setId(1L); // Sample network device ID

        NetworkDeviceAnalysis networkDeviceAnalysis = new NetworkDeviceAnalysis();
        networkDeviceAnalysis.setActiveDevice(100);
        networkDeviceAnalysis.setAverageTimeUsage(60);
        networkDeviceAnalysis.setCompanyRating(4);

        networkDeviceDTO.setNetworkDevice(networkDevice);
        networkDeviceDTO.setNetworkDeviceAnalysis(networkDeviceAnalysis);

        // Mock the behavior of the networkDeviceRepository
        when(networkDeviceRepository.findById(networkDevice.getId())).thenReturn(Optional.of(networkDevice));

        // Mock the behavior of the networkDeviceAnalysisRepository
        when(networkDeviceAnalysisRepository.save(networkDeviceAnalysis)).thenReturn(networkDeviceAnalysis);

        // Call the method to be tested
        ResponseEntity<ResponseDTO> response = networkDeviceService.setNetworkDeviceAnalysis(networkDeviceDTO);

        // Assertions
        assertEquals("Network device analysis added successfully", response.getBody().getResponseBody());
    }

    @Test
    void testSetNetworkDeviceAnalysisDeviceNotFound() {
        NetworkDeviceDTO networkDeviceDTO = new NetworkDeviceDTO();
        NetworkDevice networkDevice = new NetworkDevice();
        networkDevice.setId(1L); // Sample network device ID

        NetworkDeviceAnalysis networkDeviceAnalysis = new NetworkDeviceAnalysis();
        networkDeviceAnalysis.setActiveDevice(100);
        networkDeviceAnalysis.setAverageTimeUsage(60);
        networkDeviceAnalysis.setCompanyRating(3);

        networkDeviceDTO.setNetworkDevice(networkDevice);
        networkDeviceDTO.setNetworkDeviceAnalysis(networkDeviceAnalysis);

        // Mock the behavior of the networkDeviceRepository to return an empty optional (device not found)
        when(networkDeviceRepository.findById(networkDevice.getId())).thenReturn(Optional.empty());

        // Call the method to be tested
        ResponseEntity<ResponseDTO> response = networkDeviceService.setNetworkDeviceAnalysis(networkDeviceDTO);

        // Assertions
        assertEquals(ERROR_MESSAGE, response.getBody().getResponseBody());
    }
    
    @Test
    void testSetNetworkDeviceRMASuccess() {
        // Create a sample NetworkDeviceDTO with a networkDevice and RMA
        NetworkDeviceDTO networkDeviceDTO = new NetworkDeviceDTO();
        NetworkDevice networkDevice = new NetworkDevice();
        networkDevice.setId(1L);
        NetworkDeviceRMA networkDeviceRMA = new NetworkDeviceRMA();
        networkDeviceRMA.setActionType("Replacement");
         networkDeviceRMA.setAmount(BigDecimal.valueOf(100.0));
        networkDeviceRMA.setDateOfAction(Date.valueOf(LocalDate.now()));
        networkDeviceRMA.setReason("Defective device");
        networkDeviceDTO.setNetworkDevice(networkDevice);
        networkDeviceDTO.setNetworkDeviceRMA(networkDeviceRMA);

        // Mock the behavior of networkDeviceRepository and networkDeviceRMARepository
        when(networkDeviceRepository.findById(1L)).thenReturn(Optional.of(networkDevice));
        when(networkDeviceRMARepository.save(networkDeviceRMA)).thenReturn(networkDeviceRMA);

        // Perform the test
        ResponseEntity<ResponseDTO> responseEntity = networkDeviceService.setNetworkDeviceRMA(networkDeviceDTO);

        // Verify the expected behavior
      
        assertEquals("Network device RMA added successfully", responseEntity.getBody().getResponseBody());
    }

    @Test
    void testSetNetworkDeviceRMANetworkDeviceNotFound() {
        // Create a sample NetworkDeviceDTO with a networkDevice and RMA
        NetworkDeviceDTO networkDeviceDTO = new NetworkDeviceDTO();
        NetworkDevice networkDevice = new NetworkDevice();
        networkDevice.setId(1L);
        NetworkDeviceRMA networkDeviceRMA = new NetworkDeviceRMA();
        networkDeviceRMA.setActionType("Replacement");
        networkDeviceRMA.setAmount(BigDecimal.valueOf(100.0));
        networkDeviceRMA.setDateOfAction(Date.valueOf(LocalDate.now()));
        networkDeviceRMA.setReason("Defective device");
        networkDeviceDTO.setNetworkDevice(networkDevice);
        networkDeviceDTO.setNetworkDeviceRMA(networkDeviceRMA);

        // Mock the behavior of networkDeviceRepository to return an empty optional
        when(networkDeviceRepository.findById(1L)).thenReturn(Optional.empty());

        // Perform the test
        ResponseEntity<ResponseDTO> responseEntity = networkDeviceService.setNetworkDeviceRMA(networkDeviceDTO);

        // Verify the expected behavior for a not found network device
        assertEquals(ERROR_MESSAGE, responseEntity.getBody().getResponseBody());
    }




     @Test
    void testAddNetworkDeviceHistory() {
        // Create a sample NetworkDevice for testing
        NetworkDevice networkDevice = new NetworkDevice();
        networkDevice.setHardwareName("TestDevice");
        networkDevice.setPurchaseDate(new Date(System.currentTimeMillis()));
        networkDevice.setWarrantyEndDate(new Date(System.currentTimeMillis()));
        networkDevice.setSerialNumber("12345");
        networkDevice.setCost(BigDecimal.valueOf(1000));
        networkDevice.setQuantity(1);

        // Mock the behavior of the networkDeviceHistoryRepository
        Mockito.when(networkDeviceHistoryRepository.save(Mockito.any(NetworkDevicesHistory.class)))
                .thenAnswer(invocation -> {
                    NetworkDevicesHistory savedHistory = invocation.getArgument(0);
                    savedHistory.setId(2L); // Simulate that the history gets an ID when saved
                    return savedHistory;
                });
        // Call the method you want to test
        ResponseEntity<ResponseDTO> responseEntity = networkDeviceService.addNetworkDeviceHistory(networkDevice, "ADD");

        // Verify the expected behavior
     
        assertEquals("Network device history added successfully", responseEntity.getBody().getResponseBody());
    }



    @Test
     void testDeleteNetworkDeviceById() {
        // Create a sample network device ID for testing
        Long deviceId = 1L;

        // Mock the behavior of the networkDeviceRepository
        NetworkDevice mockNetworkDevice = new NetworkDevice();
        Mockito.when(networkDeviceRepository.findById(deviceId))
               .thenReturn(Optional.of(mockNetworkDevice)); // Mock the findById operation
        Mockito.doNothing().when(networkDeviceRepository).deleteById(deviceId); // Mock the delete operation

        // Call the method you want to test
        ResponseEntity<ResponseDTO> responseEntity = networkDeviceService.deleteNetworkDeviceById(deviceId);

        // Verify the expected behavior
        assertEquals("Network device deleted successfully", responseEntity.getBody().getResponseBody());
    }

    @Test
    void testDeleteNetworkDeviceById_NotFound() {
        // Create a sample network device ID for testing
        Long deviceId = 1L;

        // Mock the behavior of the networkDeviceRepository to return an empty optional
        Mockito.when(networkDeviceRepository.findById(deviceId))
               .thenReturn(Optional.empty());

        // Call the method you want to test
        ResponseEntity<ResponseDTO> responseEntity = networkDeviceService.deleteNetworkDeviceById(deviceId);

        // Verify the expected behavior for a not found device
      
        assertEquals(ERROR_MESSAGE, responseEntity.getBody().getResponseBody());
    }


    @Test
    void testGetNetworkDeviceHistory_WithData() {
        // Create some sample data for network device history
        NetworkDevicesHistory history1 = new NetworkDevicesHistory();
        history1.setId(1L);

        NetworkDevicesHistory history2 = new NetworkDevicesHistory();
        history2.setId(2L);

        List<NetworkDevicesHistory> sampleHistoryList = new ArrayList<>();
        sampleHistoryList.add(history1);
        sampleHistoryList.add(history2);

        // Mock the behavior of the networkDeviceHistoryRepository
        when(networkDeviceHistoryRepository.findAll()).thenReturn(sampleHistoryList);

        // Call the method you want to test
        List<NetworkDevicesHistory> result = networkDeviceService.getNetworkDeviceHistory();

        // Assert that the result matches the sample data
        assertEquals(sampleHistoryList, result);
    }

    @Test
    void testGetNetworkDeviceHistory_EmptyData() {
        // Mock the behavior of the networkDeviceHistoryRepository to return an empty list
        when(networkDeviceHistoryRepository.findAll()).thenReturn(new ArrayList<>());

        // Call the method you want to test
        List<NetworkDevicesHistory> result = networkDeviceService.getNetworkDeviceHistory();

        // Assert that the result is an empty list
        assertEquals(new ArrayList<>(), result);
    }

    
    @Test
    void testGetNetworkDeviceAnalysis_WithData() {
        // Create some sample data for network device analysis
        NetworkDeviceAnalysis analysis1 = new NetworkDeviceAnalysis();
        analysis1.setId(1L);

        NetworkDeviceAnalysis analysis2 = new NetworkDeviceAnalysis();
        analysis2.setId(2L);

        List<NetworkDeviceAnalysis> sampleAnalysisList = new ArrayList<>();
        sampleAnalysisList.add(analysis1);
        sampleAnalysisList.add(analysis2);

        // Mock the behavior of the networkDeviceAnalysisRepository
        when(networkDeviceAnalysisRepository.findAll()).thenReturn(sampleAnalysisList);

        // Call the method you want to test
        List<NetworkDeviceAnalysis> result = networkDeviceService.getNetworkDeviceAnalysis();

        // Assert that the result matches the sample data
        assertEquals(sampleAnalysisList, result);
    }

    @Test
    void testGetNetworkDeviceAnalysis_EmptyData() {
        // Mock the behavior of the networkDeviceAnalysisRepository to return an empty list
        when(networkDeviceAnalysisRepository.findAll()).thenReturn(new ArrayList<>());

        // Call the method you want to test
        List<NetworkDeviceAnalysis> result = networkDeviceService.getNetworkDeviceAnalysis();

        // Assert that the result is an empty list
        assertEquals(new ArrayList<>(), result);
    }


    @Test
    void testGetNetworkDeviceRMA_WithData() {
        // Create some sample data for network device RMA
        NetworkDeviceRMA rma1 = new NetworkDeviceRMA();
        rma1.setId(1L);

        NetworkDeviceRMA rma2 = new NetworkDeviceRMA();
        rma2.setId(2L);

        List<NetworkDeviceRMA> sampleRMAList = new ArrayList<>();
        sampleRMAList.add(rma1);
        sampleRMAList.add(rma2);

        // Mock the behavior of the networkDeviceRMARepository
        when(networkDeviceRMARepository.findAll()).thenReturn(sampleRMAList);

        // Call the method you want to test
        List<NetworkDeviceRMA> result = networkDeviceService.getNetworkDeviceRMA();

        // Assert that the result matches the sample data
        assertEquals(sampleRMAList, result);
    }

    @Test
    void testGetNetworkDeviceRMA_EmptyData() {
        // Mock the behavior of the networkDeviceRMARepository to return an empty list
        when(networkDeviceRMARepository.findAll()).thenReturn(new ArrayList<>());

        // Call the method you want to test
        List<NetworkDeviceRMA> result = networkDeviceService.getNetworkDeviceRMA();

        // Assert that the result is an empty list
        assertEquals(new ArrayList<>(), result);
    }


     @Test
    void testDeleteNetworkDeviceRMAById_ExistingRMA() {
        // Create a sample NetworkDeviceRMA with a known ID
        Long rmaId = 1L;
        NetworkDeviceRMA networkDeviceRMA = new NetworkDeviceRMA();
        networkDeviceRMA.setId(rmaId);

        // Mock the behavior of the networkDeviceRMARepository to return the sample RMA
        when(networkDeviceRMARepository.findById(rmaId)).thenReturn(Optional.of(networkDeviceRMA));

        // Call the method you want to test
        ResponseEntity<ResponseDTO> responseEntity = networkDeviceService.deleteNetworkDeviceRMAById(rmaId);

        // Verify that the RMA was deleted and the response message is as expected
        verify(networkDeviceRMARepository).deleteById(rmaId);
        verify(networkDeviceAnalysisRepository).deleteByNetworkDeviceId(rmaId);
        assertEquals("Network device RMA deleted successfully", responseEntity.getBody().getResponseBody());
    }

    @Test
    void testDeleteNetworkDeviceRMAById_NonExistentRMA() {
        // Create a sample RMA ID that doesn't exist in the repository
        Long nonExistentRmaId = 999L;

        // Mock the behavior of the networkDeviceRMARepository to return an empty optional
        when(networkDeviceRMARepository.findById(nonExistentRmaId)).thenReturn(Optional.empty());

        // Call the method you want to test
        ResponseEntity<ResponseDTO> responseEntity = networkDeviceService.deleteNetworkDeviceRMAById(nonExistentRmaId);

        // Verify that the method returns the expected error message
        assertEquals(ERROR_MESSAGE, responseEntity.getBody().getResponseBody());
    }


    
   


   
    
}
