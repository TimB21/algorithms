# Lens Quality Analysis and Repository Management System

## Introduction

Welcome to the **Lens Quality Analysis and Repository Management System**! This project demonstrates the development of a web-based application for managing lens data, showcasing expertise in **Java**, **Spring Boot**, **SQL**, and web technologies such as **HTML**, **CSS**, and **JavaScript**.

The system is designed to analyze and store lens transmission and absorption spectrum data, providing users with CRUD functionality and an intuitive API to manage their data.

---

## Core Features

### Lens Quality Analysis

- **Transmission Spectrum Analysis**: Analyzes transmission data across wavelengths to determine how much light passes through the lens.
- **Absorption Spectrum Analysis**: Evaluates absorption patterns to assess eye protection capabilities.

**Performance Metrics Calculation:**

- **R² (Coefficient of Determination)**: Measures the linear relationship between wavelength and transmission.
- **Melatonin Production Factor (MPF)**: Evaluates blue light blocking efficiency to support melatonin production.
- **Eye Protection Factor (EPF)**: Assesses the lens's UV protection based on UV light absorption.
- **Glare Reduction Factor (GRF)**: Quantifies the lens's effectiveness in reducing glare.

### Lens Data Management

- **Create**: Add new lenses with transmission and absorption data.
- **Read**: Retrieve and view lens records.
- **Update**: Modify existing lens entries.
- **Delete**: Remove lens records securely.

### Scalable APIs

- **RESTful endpoints** for interacting with lens data.
- **Support for bulk data uploads** via file import.

### Database Integrity

- **Relational design** with user-lens associations.
- **Cascade delete** ensures consistency when users are removed.

---

## Technologies Used

### Backend

- **Java Spring Boot**: Core application logic with dependency injection.
- **PostgreSQL**: Database management for lens and user data.
- **SQL**: Query optimization and schema design.

### Frontend

- **HTML, CSS, JavaScript**: User-friendly web interface with dynamic elements.

### Supporting Libraries

- **SLF4J**: Logging and monitoring.
- **Jackson**: JSON parsing and processing.
- **JUnit**: Unit testing.

---

## How It Works

1. **Lens Object Initialization**: A Lens object is created with spectrophotometer data.
2. **Data Segmentation**: The application isolates key wavelength ranges to compute metrics like MPF, EPF, and GRF.
3. **Statistical Analysis**: Linear regression calculates R² values and other performance metrics.
4. **Database Operations**: Users can create, retrieve, update, and delete lens data through the web interface.
5. **API Interaction**: Developers can access the lens data programmatically via RESTful endpoints.


## Skills Demonstrated

- **Java Development**: Spring Boot implementation with modular architecture.
- **SQL Expertise**: Optimized database queries and relational integrity.
- **Web Development**: Interactive interface using JavaScript and responsive design principles.
- **Machine Learning Integration**: Regression analysis applied to optical data.
- **RESTful API Design**: Built scalable, maintainable API endpoints.

