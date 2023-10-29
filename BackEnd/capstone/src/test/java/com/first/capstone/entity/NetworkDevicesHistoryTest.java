package com.first.capstone.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NetworkDevicesHistoryTest {

    @Test
     void testGettersAndSetters() {
        NetworkDevicesHistory networkDevicesHistory = new NetworkDevicesHistory();
        networkDevicesHistory.setId(1L);
        networkDevicesHistory.setDeviceName("Sample Device");
        networkDevicesHistory.setSerialNumber("123456");
        networkDevicesHistory.setCost(new BigDecimal("1000.50"));
        networkDevicesHistory.setQuantity(5);
        networkDevicesHistory.setPurchaseDate(new java.sql.Date(new Date().getTime()));
        networkDevicesHistory.setWarrantyEndDate(new java.sql.Date(new Date().getTime()));
        networkDevicesHistory.setAction("Action");
        networkDevicesHistory.setDate(new java.sql.Date(new Date().getTime()));

        assertEquals(1L, networkDevicesHistory.getId());
        assertEquals("Sample Device", networkDevicesHistory.getDeviceName());
        assertEquals("123456", networkDevicesHistory.getSerialNumber());
        assertEquals(new BigDecimal("1000.50"), networkDevicesHistory.getCost());
        assertEquals(5, networkDevicesHistory.getQuantity());
        assertEquals("Action", networkDevicesHistory.getAction());
        // Similarly, test other getters and setters
    }
}
