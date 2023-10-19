// package com.first.capstone.entity;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import java.math.BigDecimal;
// import java.sql.Date;
// import java.time.LocalDate;

// @DataJpaTest
// class EntitiesTest {

//     @Mock
//     private Manufacturer manufacturer;
//     private Software software;
  
//     private NetworkDevice networkDevice;
//     private SoftwareLicenseHistory softwareLicenseHistory;
//     private SoftwareAnalysis softwareAnalysis;

//     @BeforeEach
//     void setUp() {
//         manufacturer = new Manufacturer();
//         software = new Software();

//         networkDevice = new NetworkDevice();
//         softwareLicenseHistory = new SoftwareLicenseHistory();
//         softwareAnalysis = new SoftwareAnalysis();

//     }


//     @Test
//     void testSoftwareId() {
//         Long id = 1L;
//         software.setId(id);
//         assertEquals(id, software.getId());
//     }

//     @Test
//     void testSoftwareManufacturer() {
//         software.setManufacturer(manufacturer);
//         assertEquals(manufacturer, software.getManufacturer());
//     }

//     @Test
//     void testSoftwareName() {
//         String softwareName = "Test Software";
//         software.setSoftwareName(softwareName);
//         assertEquals(softwareName, software.getSoftwareName());
//     }

//     @Test
//     void testPurchaseDate() {
//         Date purchaseDate = Date.valueOf("2023-01-15");
//         software.setPurchaseDate(purchaseDate);
//         assertEquals(purchaseDate, software.getPurchaseDate());
//     }

//     @Test
//     void testExpiryDate() {
//         Date expiryDate = Date.valueOf("2024-01-15");
//         software.setExpiryDate(expiryDate);
//         assertEquals(expiryDate, software.getExpiryDate());
//     }

//     @Test
//     void testTypeOfPlan() {
//         String typeOfPlan = "Basic Plan";
//         software.setTypeOfPlan(typeOfPlan);
//         assertEquals(typeOfPlan, software.getTypeOfPlan());
//     }

//     @Test
//     void testUsersCanUse() {
//         Integer usersCanUse = 10;
//         software.setUsersCanUse(usersCanUse);
//         assertEquals(usersCanUse, software.getUsersCanUse());
//     }

//     @Test
//     void testPriceOfSoftware() {
//         BigDecimal price = new BigDecimal("99.99");
//         software.setPriceOfSoftware(price);
//         assertEquals(price, software.getPriceOfSoftware());
//     }

//     @Test
//     void testSoftwareNotNull() {
//         assertNotNull(software);
//     }

//     @Test
//     void testId() {
//         Long id = 1L;
//         manufacturer.setId(id);
//         assertEquals(id, manufacturer.getId());
//     }

//     @Test
//     void testName() {
//         String name = "Test Manufacturer";
//         manufacturer.setName(name);
//         assertEquals(name, manufacturer.getName());
//     }

//     @Test
//     void testFieldOfWork() {
//         String fieldOfWork = "Test Field of Work";
//         manufacturer.setFieldOfWork(fieldOfWork);
//         assertEquals(fieldOfWork, manufacturer.getFieldOfWork());
//     }

//     @Test
//     void testCompanyWebsiteLink() {
//         String companyWebsiteLink = "http://example.com";
//         manufacturer.setCompanyWebsiteLink(companyWebsiteLink);
//         assertEquals(companyWebsiteLink, manufacturer.getCompanyWebsiteLink());
//     }

//     @Test
//     void testEmailId() {
//         String emailId = "test@example.com";
//         manufacturer.setEmailId(emailId);
//         assertEquals(emailId, manufacturer.getEmailId());
//     }

//     @Test
//     void testManufacturerNotNull() {
//         assertNotNull(manufacturer);
//     }

   

//     @Test
//     void testNetworkDeviceId() {
//         Long id = 1L;
//         networkDevice.setId(id);
//         assertEquals(id, networkDevice.getId());
//     }

//     @Test
//     void testNetworkDeviceManufacturer() {
//         Manufacturer manufacturer = new Manufacturer();
//         networkDevice.setManufacturer(manufacturer);
//         assertEquals(manufacturer, networkDevice.getManufacturer());
//     }

//     @Test
//     void testHardwareName() {
//         String hardwareName = "Test Hardware";
//         networkDevice.setHardwareName(hardwareName);
//         assertEquals(hardwareName, networkDevice.getHardwareName());
//     }

//     @Test
//     void testPurchaseDateOfNetworkDeices() {
//         LocalDate purchaseDate = LocalDate.of(2023, 1, 15);
//         networkDevice.setPurchaseDate(Date.valueOf(purchaseDate));
//         assertEquals(Date.valueOf(purchaseDate), networkDevice.getPurchaseDate());
//     }

//     @Test
//     void testWarrantyEndDate() {
//         LocalDate warrantyEndDate = LocalDate.of(2024, 1, 15);
//         networkDevice.setWarrantyEndDate(Date.valueOf(warrantyEndDate));
//         assertEquals(Date.valueOf(warrantyEndDate), networkDevice.getWarrantyEndDate());
//     }



//     @Test
//     void testSerialNumber() {
//         String serialNumber = "123456";
//         networkDevice.setSerialNumber(serialNumber);
//         assertEquals(serialNumber, networkDevice.getSerialNumber());
//     }

//     @Test
//     void testNetworkDeviceNotNull() {
//         assertNotNull(networkDevice);
//     }


//     @Test
//     void testSoftwareLicenseHistoryId() {
//         Long id = 1L;
//         softwareLicenseHistory.setId(id);
//         assertEquals(id, softwareLicenseHistory.getId());
//     }

//     @Test
//     void testSoftware() {
//         Software software = new Software();
//         softwareLicenseHistory.setSoftware(software);
//         assertEquals(software, softwareLicenseHistory.getSoftware());
//     }

//     @Test
//     void testLicenseKey() {
//         String licenseKey = "ABC123";
//         softwareLicenseHistory.setLicenseKey(licenseKey);
//         assertEquals(licenseKey, softwareLicenseHistory.getLicenseKey());
//     }

//     @Test
//     void testSoftwareLicenseHistoryNotNull() {
//         assertNotNull(softwareLicenseHistory);
//     }

//     @Test
//     void testSoftwareAnalysisId() {
//         Long id = 1L;
//         softwareAnalysis.setId(id);
//         assertEquals(id, softwareAnalysis.getId());
//     }

//     @Test
//     void testAverageTimeUsage() {
//         double averageTimeUsage = 10.5;
//         softwareAnalysis.setAverageTimeUsage(averageTimeUsage);
//         assertEquals(averageTimeUsage, softwareAnalysis.getAverageTimeUsage(), 0.01); // The third parameter is the delta for floating-point comparison
//     }

//     @Test
//     void testActiveUsers() {
//         int activeUsers = 50;
//         softwareAnalysis.setActiveUsers(activeUsers);
//         assertEquals(activeUsers, softwareAnalysis.getActiveUsers());
//     }

//     @Test
//     void testGlobalRating() {
//         double globalRating = 4.5;
//         softwareAnalysis.setGlobalRating(globalRating);
//         assertEquals(globalRating, softwareAnalysis.getGlobalRating(), 0.01); // Delta for floating-point comparison
//     }

//     @Test
//     void testSoftwareinAnalysis() {
//         Software software = new Software();
//         softwareAnalysis.setSoftware(software);
//         assertEquals(software, softwareAnalysis.getSoftware());
//     }

//     @Test
//     void testSoftwareAnalysisNotNull() {
//         assertNotNull(softwareAnalysis);
//     }

// }