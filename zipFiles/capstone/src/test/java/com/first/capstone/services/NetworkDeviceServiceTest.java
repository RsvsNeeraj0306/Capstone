package com.first.capstone.services;

import com.first.capstone.dto.NetworkDeviceDTO;
import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.NetworkDevice;
import com.first.capstone.entity.NetworkDevicesHistory;
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
    private NetworkDevice networkDevice;

   

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
    
        networkDeviceService = new NetworkDeviceService(networkDeviceRepository, manufacturerService, networkDeviceHistoryRepository);
    
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
        assertEquals(200, responseEntity.getStatusCodeValue()); // Assuming a successful response code
        assertEquals("Network device history added successfully", responseEntity.getBody().getResponseBody());
    }




    // @Test
    // void testAddNetworkDeviceHistory_DeviceNotFound() {
    //     // Create a sample NetworkDeviceDTO with a networkDevice
    //     NetworkDeviceDTO networkDeviceDTO = new NetworkDeviceDTO();

    //     // Create a sample network device
    //     NetworkDevice networkDevice = new NetworkDevice();
    //     networkDevice.setId(1L);
    //     networkDeviceDTO.setNetworkDevice(networkDevice);

    //     // Mock the behavior of the networkDeviceRepository to return an existing
    //     // network device
    //     when(networkDeviceRepository.findById(1L)).thenReturn(Optional.empty());

    //     // Mock the behavior of networkDeviceHistoryRepository when saving the new
    //     // history
    //     Mockito.when(networkDeviceHistoryRepository.save(Mockito.any(NetworkDevicesHistory.class)))
    //             .thenAnswer(invocation -> {
    //                 NetworkDevicesHistory savedHistory = invocation.getArgument(0);
    //                 savedHistory.setId(2L); // Simulate that the history gets an ID when saved
    //                 return savedHistory;
    //             });
    //     // Perform the test
    //     ResponseEntity<ResponseDTO> responseEntity = networkDeviceService.addNetworkDeviceHistory(networkDeviceDTO);

    //     // Assertions
    //     assertEquals("Network device not found", responseEntity.getBody().getResponseBody());
    // }

    // @Test
    // void testAddNetworkDeviceHistory_DeviceFound() {
    //     // Create a sample NetworkDeviceDTO without a networkDevice
    //     NetworkDeviceDTO networkDeviceDTO = new NetworkDeviceDTO();
    //     NetworkDevice networkDevice = new NetworkDevice();
    //     networkDevice.setId(1L);
    //     networkDeviceDTO.setNetworkDevice(networkDevice);
       
    //     // Perform the test
    //     ResponseEntity<ResponseDTO> responseEntity = networkDeviceService.addNetworkDeviceHistory(networkDeviceDTO);

    //     assertEquals("Network device added successfully", responseEntity.getBody().getResponseBody());
    // }

    // @Test
    // void testGetters() {
    //     NetworkDevicesHistory networkDevicesHistory = new NetworkDevicesHistory();

    //     // Set values for the network devices history
    //     networkDevicesHistory.setId(1L);
    //     networkDevicesHistory.setNetworkDevice(networkDevice);
    //     networkDevicesHistory.setLicenseKey("TestLicenseKey");
    //     networkDevicesHistory.setWarrantyEndDate(Date.valueOf(LocalDate.now().plusDays(30)));
    //     networkDevicesHistory.setPurchaseDate(Date.valueOf(LocalDate.now()));

    //     // Check the getters
    //     assertEquals(1L, networkDevicesHistory.getId());
    //     assertEquals(networkDevice, networkDevicesHistory.getNetworkDevice());
    //     assertEquals("TestLicenseKey", networkDevicesHistory.getLicenseKey());
    //     assertEquals(Date.valueOf(LocalDate.now().plusDays(30)), networkDevicesHistory.getWarrantyEndDate());
    //     assertEquals(Date.valueOf(LocalDate.now()), networkDevicesHistory.getPurchaseDate());
    // }


    @Test
    public void testDeleteNetworkDeviceById() {
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
        assertEquals(200, responseEntity.getStatusCodeValue()); // Assuming a successful response code
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
      
        assertEquals("Network device not found", responseEntity.getBody().getResponseBody());
    }
   


   
    
}
