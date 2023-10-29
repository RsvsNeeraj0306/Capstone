package com.first.capstone.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManufactureHistoryTest {

    @Test
    void testGettersAndSetters() {
        ManufactureHistory manufactureHistory = new ManufactureHistory();
        manufactureHistory.setId(1L);
        manufactureHistory.setName("Sample Manufacturer");
        manufactureHistory.setFieldOfWork("Sample Field of Work");
        manufactureHistory.setCompanyWebsiteLink("http://example.com");
        manufactureHistory.setEmailId("sample@example.com");
        manufactureHistory.setAction("Action");
        manufactureHistory.setDate(new java.sql.Date(new java.util.Date().getTime()));

        assertEquals(1L, manufactureHistory.getId());
        assertEquals("Sample Manufacturer", manufactureHistory.getName());
        assertEquals("Sample Field of Work", manufactureHistory.getFieldOfWork());
        assertEquals("http://example.com", manufactureHistory.getCompanyWebsiteLink());
        assertEquals("sample@example.com", manufactureHistory.getEmailId());
        assertEquals("Action", manufactureHistory.getAction());
        // Similarly, test other getters and setters
    }
}
