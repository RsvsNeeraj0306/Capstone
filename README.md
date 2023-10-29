# License Management System (LMS)

## Table of Contents
1. [System Overview](#system-overview)
2. [Code Links](#Links)
3. [Functional Requirements](#functional-requirements)
4. [Additional Features](#additional-features)
5. [Related Documents](#related-documents)
6. [Technology Stack](#technology-stack)
7. [Key Features](#key-features)
8. [Getting Started](#getting-started)
9. [Testing and Quality Assurance](#testing-and-quality-assurance)
10. [Data Visualization](#data-visualization)

## System Overview
The License Management System (LMS) is a web-based application designed to assist enterprises in effectively managing their network devices and software licenses. LMS provides a user-friendly dashboard for real-time monitoring of license statuses, automated email notifications to remind users about expiring licenses, and a user interface for easy license status updates.

## Links
- [Frontend Repository](https://github.com/RsvsNeeraj0306/Capstone/tree/main/UpdatedNewFrontEnd/src)
- [Backend Repository](https://github.com/RsvsNeeraj0306/Capstone/tree/main/zipFiles/capstone/src)

## Functional Requirements
- [x] Ability to track network devices and software licenses within an organization.
- [x] Automatic email notifications sent at regular intervals to alert users about impending license expirations.
- [x] A responsive user interface dashboard for tracking and managing license statuses.
- [x] User-friendly interface for updating license statuses.

## Additional Features
- [x] Automated notifications for impending license expirations to ensure timely renewal.
- [x] Comprehensive maintenance of essential information, including device IDs, purchase dates, expiration dates, and renewal requirements.
- [x] Holistic license and lifecycle management to track the current status and lifecycle stage (e.g., commissioning, relocation, return merchandise authorization).

## Related Documents
- [x] [Backend API Documentation](https://github.com/RsvsNeeraj0306/Capstone/blob/main/apidocu.md)
- [x] [Frontend Documentation](https://github.com/RsvsNeeraj0306/Capstone/blob/main/frontendDocument.md)
- [x] [Code Coverage Report](https://github.com/RsvsNeeraj0306/Capstone/blob/main/capstone1.PNG)
- [x] [Database Schema](https://github.com/RsvsNeeraj0306/Capstone/blob/main/ERCapstone.PNG)

## Technology Stack
- **Frontend**: React, JavaScript, Material-UI components, Sass
- **Backend**: Java 11, Spring Boot
- **Database**: MySQL
- **Testing Frameworks**: Mockito, Selenium
- **Data Visualization Libraries**: Chart.js

## Key Features
1. **Device and Software Tracking**: Maintain a comprehensive record of network devices and software licenses. Capture relevant details such as device/software name, license number, vendor name, purchase date, expiry date, and more.

2. **Email Notifications**: Automatically send email notifications at predefined intervals to remind users about upcoming license expirations. Empower users to take timely actions for license renewal.

3. **User-Friendly Dashboard**: Access an aesthetically pleasing dashboard that offers a quick overview of current license statuses. Quickly identify active, expired, or soon-to-expire licenses.

4. **License Status Updates**: Effortlessly update license statuses through the intuitive user interface. Mark licenses as active, expired, or with custom status labels.

5. **License Expiration Alerts**: LMS automatically detects licenses nearing expiration based on expiry dates. Generate notifications for administrators and relevant stakeholders.

## Getting Started
To initiate your journey with the License Management System (LMS), follow these steps:

1. **Repository Cloning**: Clone the LMS GitHub repositories to your local development environment.

2. **Backend Configuration**:
   - Set up your MySQL database and configure the database connection in the Spring Boot application.properties file.
   - Adjust email settings in the application.properties file, including SMTP server details and email credentials.

3. **Run the Backend**: Launch the Spring Boot backend by executing the main application class. The LMS backend operates on port 8080, accessible at `http://localhost:8080` or your server-specific URL.

4. **Frontend Configuration**:
   - Navigate to the `frontend` directory.
   - Install frontend dependencies using `npm install`.
   - Configure the backend API URL in the frontend code as required.

5. **Frontend Execution**: Start the React frontend by running `npm start`.

6. **Access the Application**: Open your web browser and navigate to the URL where the frontend is hosted (typically `http://localhost:3000` by default).

## Testing and Quality Assurance
The License Management System employs Mockito for unit testing to ensure application reliability and correctness. Selenium is utilized for visual testing to confirm proper rendering of UI components.


