package com.first.capstone.entity;
import static org.junit.jupiter.api.Assertions.assertEquals;


import com.first.capstone.respositories.SoftwareLicenseHistoryRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Date;


@SpringBootTest
class EntitiesTest {

    @Autowired
    private SoftwareLicenseHistoryRepository repository;


    @Test
     void testSoftwareLicenseHistoryEntity() {
        // Create a sample SoftwareLicenseHistory object
        SoftwareLicenseHistory history = new SoftwareLicenseHistory();
        history.setSoftwareName("Sample Software");
        history.setLicenseKey("ABC123");
        history.setPurchaseDate(Date.valueOf("2023-10-15"));
        history.setExpiryDate(Date.valueOf("2023-12-31"));
        history.setTypeOfPlan("Standard");
        history.setQuantity(10);
        history.setPriceOfSoftware(BigDecimal.valueOf(199.99));
        history.setVersion("1.0");
        history.setAction("Purchase");
        history.setDate(Date.valueOf("2023-10-15"));

        // Save the entity to the repository
        repository.save(history);

        // Retrieve the saved entity from the repository
        SoftwareLicenseHistory savedHistory = repository.findAll().get(0);

        // Assert that the saved entity matches the original
        assertEquals("Sample Software", savedHistory.getSoftwareName());
        assertEquals("ABC123", savedHistory.getLicenseKey());
        assertEquals(Date.valueOf("2023-10-15"), savedHistory.getPurchaseDate());
        assertEquals(Date.valueOf("2023-12-31"), savedHistory.getExpiryDate());
        assertEquals("Standard", savedHistory.getTypeOfPlan());
        assertEquals(10, savedHistory.getQuantity());
        assertEquals(BigDecimal.valueOf(199.99), savedHistory.getPriceOfSoftware());
        assertEquals("1.0", savedHistory.getVersion());
        assertEquals("Purchase", savedHistory.getAction());
        assertEquals(Date.valueOf("2023-10-15"), savedHistory.getDate());
    }
}
