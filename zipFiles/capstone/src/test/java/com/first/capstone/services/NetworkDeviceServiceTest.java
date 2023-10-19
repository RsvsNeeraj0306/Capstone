package com.first.capstone.services;


import com.first.capstone.entity.NetworkDevice;
import com.first.capstone.respositories.NetworkDeviceRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

 class NetworkDeviceServiceTest {

    @InjectMocks
    private NetworkDeviceService networkDeviceService;

    @Mock
    private NetworkDeviceRepository networkDeviceRepository;

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

        // Mock the behavior of the networkDeviceRepository for an existing device
        Mockito.when(networkDeviceRepository.findByIdAndHardwareName(1L, "TestDevice"))
                .thenReturn(Optional.of(networkDevice));

        // Perform the test
        NetworkDevice result = networkDeviceService.getOrCreaNetworkDevice(networkDevice);

        // Assertions
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("TestDevice", result.getHardwareName());
    }

    @Test
    void testGetOrCreateNetworkDeviceCreateNew() {
        // Create a NetworkDevice object for testing
        NetworkDevice networkDevice = new NetworkDevice();
        networkDevice.setId(1L);
        networkDevice.setHardwareName("TestDevice");

        // Mock the behavior of the networkDeviceRepository to return an empty Optional
        Mockito.when(networkDeviceRepository.findByIdAndHardwareName(1L, "TestDevice"))
                .thenReturn(Optional.empty());

        // Mock the behavior of networkDeviceRepository when saving the new device
        Mockito.when(networkDeviceRepository.save(Mockito.any(NetworkDevice.class))
        ).thenAnswer(invocation -> {
            NetworkDevice savedDevice = invocation.getArgument(0);
            savedDevice.setId(2L); // Simulate that the device gets an ID when saved
            return savedDevice;
        });

        // Perform the test
        NetworkDevice result = networkDeviceService.getOrCreaNetworkDevice(networkDevice);

        // Assertions
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotEquals(1L, result.getId()); // The ID should be different after saving
        assertEquals("TestDevice", result.getHardwareName());
    }
}
