package com.first.capstone;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.Software;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.sql.Date;

 class EntitiesTest {

    @Mock
    private Manufacturer manufacturer;
    private Software software; 


    @BeforeEach
    void setUp() {
        manufacturer = new Manufacturer();
        software = new Software();
    }   

     @Test
    void testSoftwareId() {
        Long id = 1L;
        software.setId(id);
        assertEquals(id, software.getId());
    }

    @Test
    void testSoftwareManufacturer() {
        software.setManufacturer(manufacturer);
        assertEquals(manufacturer, software.getManufacturer());
    }

    @Test
    void testSoftwareName() {
        String softwareName = "Test Software";
        software.setSoftwareName(softwareName);
        assertEquals(softwareName, software.getSoftwareName());
    }

    @Test
    void testPurchaseDate() {
        Date purchaseDate = Date.valueOf("2023-01-15");
        software.setPurchaseDate(purchaseDate);
        assertEquals(purchaseDate, software.getPurchaseDate());
    }

    @Test
    void testExpiryDate() {
        Date expiryDate = Date.valueOf("2024-01-15");
        software.setExpiryDate(expiryDate);
        assertEquals(expiryDate, software.getExpiryDate());
    }

    @Test
    void testTypeOfPlan() {
        String typeOfPlan = "Basic Plan";
        software.setTypeOfPlan(typeOfPlan);
        assertEquals(typeOfPlan, software.getTypeOfPlan());
    }

    @Test
    void testUsersCanUse() {
        Integer usersCanUse = 10;
        software.setUsersCanUse(usersCanUse);
        assertEquals(usersCanUse, software.getUsersCanUse());
    }

    @Test
    void testPriceOfSoftware() {
        BigDecimal price = new BigDecimal("99.99");
        software.setPriceOfSoftware(price);
        assertEquals(price, software.getPriceOfSoftware());
    }

    @Test
    void testSoftwareNotNull() {
       assertNotNull(software);
    }



    @Test
    void testId() {
        Long id = 1L;
        manufacturer.setId(id);
        assertEquals(id, manufacturer.getId());
    }

    @Test
    void testName() {
        String name = "Test Manufacturer";
        manufacturer.setName(name);
        assertEquals(name, manufacturer.getName());
    }

    @Test
    void testFieldOfWork() {
        String fieldOfWork = "Test Field of Work";
        manufacturer.setFieldOfWork(fieldOfWork);
        assertEquals(fieldOfWork, manufacturer.getFieldOfWork());
    }

    @Test
    void testCompanyWebsiteLink() {
        String companyWebsiteLink = "http://example.com";
        manufacturer.setCompanyWebsiteLink(companyWebsiteLink);
        assertEquals(companyWebsiteLink, manufacturer.getCompanyWebsiteLink());
    }

    @Test
    void testEmailId() {
        String emailId = "test@example.com";
        manufacturer.setEmailId(emailId);
        assertEquals(emailId, manufacturer.getEmailId());
    }

    @Test
    void testManufacturerNotNull() {
        assertNotNull(manufacturer);
    }
   
}
