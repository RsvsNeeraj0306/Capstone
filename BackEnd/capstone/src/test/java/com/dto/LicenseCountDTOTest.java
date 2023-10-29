package com.dto;

import org.junit.jupiter.api.Test;

import com.first.capstone.dto.LicenseCountDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class LicenseCountDTOTest {

    @Test
     void testLicenseCountDTOGettersAndSetters() {
        // Arrange
        LicenseCountDTO licenseCountDTO = new LicenseCountDTO();
        long totalLicenses = 100;
        long licensesLessThanZero = 10;
        long licensesGreaterThan45 = 30;
        long licensesLessThan45 = 20;

        // Act
        licenseCountDTO.setTotalLicenses(totalLicenses);
        licenseCountDTO.setLicensesLessThanZero(licensesLessThanZero);
        licenseCountDTO.setLicensesGreaterThan45(licensesGreaterThan45);
        licenseCountDTO.setLicensesLessThan45(licensesLessThan45);

        // Assert
        assertEquals(totalLicenses, licenseCountDTO.getTotalLicenses());
        assertEquals(licensesLessThanZero, licenseCountDTO.getLicensesLessThanZero());
        assertEquals(licensesGreaterThan45, licenseCountDTO.getLicensesGreaterThan45());
        assertEquals(licensesLessThan45, licenseCountDTO.getLicensesLessThan45());
    }
}
