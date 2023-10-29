# Network Device and Software Management API Documentation

  

This documentation provides information about the Network Device and Software Management API. Below are the available endpoints and their descriptions:

  

## Table of Contents

  

- [Software API](#software-api)

- [Network Device API](#network-device-api)

- [Manufacturer API](#manufacturer-api)

- [Gmail API](#gmail-api)

- [Authentication API](#authentication-api)

  

## Software API


### Add Software Device

  

-  **Method:** POST

-  **URL:**  `/addSoftware`

-  **Description:** Add a new Software device to the system.

```json
{
  "software": {
    "id": 123,
    "manufacturer": {
      "id": 456,
      "name": "Sample Software Manufacturer",
      "fieldOfWork": "Software",
      "companyWebsiteLink": "https://software-manufacturer-example.com",
      "emailId": "contact@software-manufacturer-example.com"
    },
    "softwareName": "Example Software Suite",
    "purchaseDate": "2023-10-29T19:17:46.331Z",
    "expiryDate": "2024-10-29T19:17:46.331Z",
    "typeOfPlan": "Enterprise",
    "licenseKey": "ABCD-1234-EFGH-5678",
    "quantity": 25,
    "version": "3.0",
    "priceOfSoftware": 2500.00
  }
}
```



  

### Get Selected Software

 
-  **Method:** GET

-  **URL:**  `/SelectedSoftware`

-  **Description:** Retrieve a list of software by manufacturer or field of work.

  

### Get All Software

  

-  **Method:** GET

-  **URL:**  `/allSoftware`

-  **Description:** Retrieve a list of all software in the system.

  

### License Counts

  

-  **Method:** GET

-  **URL:**  `/licenseCounts`

-  **Description:** Retrieve license counts.

  

### Renew Software

  

-  **Method:** POST

-  **URL:**  `/renewSoftware`

-  **Description:** Renew software.

```json
{
  "software": {
    "id": 123,
    "manufacturer": {
      "id": 456,
      "name": "Sample Software Manufacturer",
      "fieldOfWork": "Software",
      "companyWebsiteLink": "https://software-manufacturer-example.com",
      "emailId": "contact@software-manufacturer-example.com"
    },
    "softwareName": "Example Software Suite",
    "purchaseDate": "2023-10-29T19:17:46.331Z",
    "expiryDate": "2024-10-29T19:17:46.331Z",
    "typeOfPlan": "Enterprise",
    "licenseKey": "ABCD-1234-EFGH-5678",
    "quantity": 25,
    "version": "3.0",
    "priceOfSoftware": 2500.00
  }
}

```


### Analysis

  

-  **Method:** POST

-  **URL:**  `/analysis`

-  **Description:** Analyse the plan of software.

```json
{
  "softwareAnalysis": {
    "id": 789,
    "averageTimeUsage": 6,
    "activeUsers": 20,
    "companyRating": 4.2,
    "software": {
      "id": 123,
      "manufacturer": {
        "id": 456,
        "name": "Sample Software Manufacturer",
        "fieldOfWork": "Software",
        "companyWebsiteLink": "https://software-manufacturer-example.com",
        "emailId": "contact@software-manufacturer-example.com"
      },
      "softwareName": "Example Software Suite",
      "purchaseDate": "2023-10-29T19:17:46.331Z",
      "expiryDate": "2024-10-29T19:17:46.331Z",
      "typeOfPlan": "Enterprise",
      "licenseKey": "ABCD-1234-EFGH-5678",
      "quantity": 25,
      "version": "3.0",
      "priceOfSoftware": 2500.00
    }
}
```





  

### Change Plan

  

-  **Method:** POST

-  **URL:**  `/changePlan`

-  **Description:** Update the plan of software.

```json
{
  "software": {
    "id": 123,
    "manufacturer": {
      "id": 456,
      "name": "Sample Software Manufacturer",
      "fieldOfWork": "Software",
      "companyWebsiteLink": "https://software-manufacturer-example.com",
      "emailId": "contact@software-manufacturer-example.com"
    },
    "softwareName": "Example Software Suite",
    "purchaseDate": "2023-10-29T19:17:46.331Z",
    "expiryDate": "2024-10-29T19:17:46.331Z",
    "typeOfPlan": "Enterprise",
    "licenseKey": "ABCD-1234-EFGH-5678",
    "quantity": 25,
    "version": "3.0",
    "priceOfSoftware": 2500.00
  }
}
```


  

### Delete Software

  

-  **Method:** DELETE

-  **URL:**  `/deleteSoftware/{softwareId}`

-  **Description:** Delete software by its ID.

  

### Get Software Less Than 45 Days

  

-  **Method:** GET

-  **URL:**  `/getSoftwareLessThan45days`

-  **Description:** Retrieve software with less than 45 days left in its license.

  

### Get Software Less Than Zero Days

  

-  **Method:** GET

-  **URL:**  `/getSoftwareLessThanZerodays`

-  **Description:** Retrieve software with an expired license (zero days left).

  

## Network Device API

  

### Get All Network Devices

  

-  **Method:** GET

-  **URL:**  `/getAllNetworkDevices`

-  **Description:** Retrieve a list of all network devices.

  

### Add Network Device

  

-  **Method:** POST

-  **URL:**  `/addNetworkDevices`

-  **Description:** Add a new network device to the system.

```json
{
  "networkDevice": {
    "id": 1234,
    "manufacturer": {
      "id": 5678,
      "name": "Example Manufacturer",
      "fieldOfWork": "Hardware",
      "companyWebsiteLink": "https://manufacturer-example.com",
      "emailId": "contact@example-manufacturer.com"
    },
    "hardwareName": "Sample Device",
    "purchaseDate": "2023-10-29T19:06:41.733Z",
    "warrantyEndDate": "2024-10-29T19:06:41.733Z",
    "location": "Office A",
    "serialNumber": "ABCD-1234-5678",
    "quantity": 5,
    "cost": 5000
  }
}
```


  

### Add Network Device History

  

-  **Method:** POST

-  **URL:**  `/addNetworkDevicesHistory`

-  **Description:** Add a history entry for a network device, such as an "ADD" event.

  

### Set Network Device RMA

  

-  **Method:** POST

-  **URL:**  `/setRMA`

-  **Description:** Set the RMA status for a network device.

```json
 {
	 "networkDeviceRMA": {
	    "id": 9876,
	    "dateOfAction": "2023-11-15T14:30:00.000Z",
    "reason": "Defective Hardware",
    "actionType": "Replacement",
    "amount": 1000,
    "networkDevice": {
      "id": 1234,
      "manufacturer": {
        "id": 5678,
        "name": "Example Manufacturer",
        "fieldOfWork": "Hardware",
        "companyWebsiteLink": "https://manufacturer-example.com",
        "emailId": "contact@example-manufacturer.com"
      },
      "hardwareName": "Sample Device",
      "purchaseDate": "2023-10-29T19:06:41.733Z",
      "warrantyEndDate": "2024-10-29T19:06:41.733Z",
      "location": "Office A",
      "serialNumber": "ABCD-1234-5678",
      "quantity": 5,
      "cost": 5000
    }
  }
}
```

  

### Set Network Device Analysis

  

-  **Method:** POST

-  **URL:**  `/setAnalysis`

-  **Description:** Set the analysis information for a network device.

```json
{
 "networkDeviceAnalysis": {
    "id": 4321,
    "networkDevice": {
      "id": 1234,
      "manufacturer": {
        "id": 5678,
        "name": "Example Manufacturer",
        "fieldOfWork": "Hardware",
        "companyWebsiteLink": "https://manufacturer-example.com",
        "emailId": "contact@example-manufacturer.com"
      },
      "hardwareName": "Sample Device",
      "purchaseDate": "2023-10-29T19:06:41.733Z",
      "warrantyEndDate": "2024-10-29T19:06:41.733Z",
      "location": "Office A",
      "serialNumber": "ABCD-1234-5678",
      "quantity": 5,
      "cost": 5000
    },
    "activeDevice": 3,
    "averageTimeUsage": 8,
    "companyRating": 4
  }
}
  ```

  

### Get Network Device History

  

-  **Method:** GET

-  **URL:**  `/getNetworkDeviceHistory`

-  **Description:** Retrieve a list of history entries for network devices.

  

### Delete Network Device

  

-  **Method:** DELETE

-  **URL:**  `/deleteNetworkDevice/{id}`

-  **Description:** Delete a network device by its ID.

  

### Get Network Device RMA

  

-  **Method:** GET

-  **URL:**  `/getNetworkDeviceRMA`

-  **Description:** Retrieve a list of network devices with their RMA status.

  

### Get Network Device Analysis

  

-  **Method:** GET

-  **URL:**  `/getNetworkDeviceAnalysis`

-  **Description:** Retrieve a list of network devices with their analysis information.

  

### Delete Network Device RMA

  

-  **Method:** DELETE

-  **URL:**  `/deleteNetworkDeviceRMA/{id}`

-  **Description:** Delete the RMA information for a network device by its ID.

  

## Manufacturer API

  

### Create Manufacturer

  

-  **Method:** POST

-  **URL:**  `/api/addManufacturer`

-  **Description:** Create a new manufacturer or retrieve an existing one.

```json
{
  "id": 1,
  "name": "ManufactureName",
  "fieldOfWork": "Software",
  "companyWebsiteLink": "https://example.com",
  "emailId": "john.doe@example.com"
}
```


  

### Get All Manufacturers

  

-  **Method:** GET

-  **URL:**  `/api/allManufacture`

-  **Description:** Retrieve a list of all manufacturers.

  

### Get All Manufacturers by Software

  

-  **Method:** GET

-  **URL:**  `/api/allManufactureBySoftware`

-  **Description:** Retrieve a list of all manufacturers specializing in software products.

  

### Get All Manufacturers by Hardware

  

-  **Method:** GET

-  **URL:**  `/api/allManufactureByHardware`

-  **Description:** Retrieve a list of all manufacturers specializing in hardware products.

  

### Get Manufacturer by ID

  

-  **Method:** GET

-  **URL:**  `/api/getManufacturerById/{id}`

-  **Description:** Retrieve a manufacturer by its unique ID.

  

### Update Manufacturer

  

-  **Method:** PUT

-  **URL:**  `/api/updateManufacturer/{id}`

-  **Description:** Update an existing manufacturer's information.

  

### Get Manufacturer History

  

-  **Method:** GET

-  **URL:**  `/api/getManufacturerHistory`

-  **Description:** Retrieve a list of manufacturer history entries.

  

## Gmail API

  

### Test Endpoint

  

-  **Method:** GET

-  **URL:**  `/api/test`

-  **Description:** Sends a test email and returns "test" as a response.

  

### Send Email

  

-  **Method:** POST

-  **URL:**  `/api/sendMail`

-  **Description:** Send an email using the Gmail service.

```json
{
  "responseBody": "string"
}
```

  

## Authentication API

  

### Generate Token

  

-  **Method:** POST

-  **URL:**  `/token`

-  **Description:** Generate an authentication token for a user.

```json
{
  "username": "neeraj",
  "password": "neeraj"
}
```