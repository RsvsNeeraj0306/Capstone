# Software Requirement Specification (SRS)

## Project: License Lifecycle Management (LLM) Application

### Table of Contents
1. [Introduction](#1-introduction)
2. [Project Description](#2-project-description)
3. [Functional Requirements](#3-functional-requirements)
4. [Dev/Execution Platform](#4-devexecution-platform)
5. [References](#5-references)
6. [Remarks](#6-remarks)

---

### 1. Introduction
This document serves as a Software Requirement Specification (SRS) for the License Lifecycle Management (LLM) Application. The purpose of this application is to track and manage the license details of network devices and software within an enterprise.

### 2. Project Description
- **Author/Owner:** Vignesh
- **Project Name:** LLM
- **Description:** Build an app to track license details of network devices and software.
  
### 3. Functional Requirements
The LLM Application must fulfill the following functional requirements:

#### 3.1. License Tracking
- The application shall provide the capability to track devices and software licenses within an enterprise.
- Information to be tracked includes device/software name, device ID, date of purchase, dates of expiry, and the need to renew.

#### 3.2. Email Notifications
- The application shall send email notifications at regular intervals to remind users about license expiry.

#### 3.3. User Interface (UI) Dashboard
- The application shall maintain a UI dashboard for tracking the license status of devices and software.
- The dashboard should display all entries and provide filters for easy searching.

#### 3.4. License Status Update
- The application shall offer a UI interface to update the status of licenses.
- Users should be able to mark licenses as renewed or expired through the UI.

#### 3.5. License Lifecycle Management
- The application shall manage the entire lifecycle of licenses, including tracking the current status and lifecycle stage (e.g., commissioned, deployed to another location, RMA).
  
### 4. Dev/Execution Platform
- **Programming Language:** Java 11
- **Framework:** Spring Boot 2.7.13
- **User Interface:** React

### 5. References
- No specific reference documents or videos mentioned.

### 6. Remarks
- The backend of the application should generate notifications if a license is expiring.
- The application should maintain crucial information such as device ID, date of purchase, dates of expiry, and the need for renewal.
- License management and lifecycle tracking are essential components of this application.
- The application must provide Swagger documentation for API endpoints.
- A React-based user interface is mandatory for the frontend.

---

This Software Requirement Specification (SRS) provides an overview of the License Lifecycle Management (LLM) Application's objectives and functional requirements. It serves as a guide for development and ensures that all stakeholders are aligned with the project's goals.
