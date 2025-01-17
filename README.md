# Lens Analysis and Repository Management System

## **Introduction**

Welcome to the Lens Analysis and Repository Management System! This project demonstrates the development of a web-based application for managing lens data, showcasing expertise in **Java**, **Spring Boot**, **SQL**, and web technologies such as **HTML**, **CSS**, and **JavaScript**. 

The system is designed to analyze and store lens transmission and absorption spectrum data, providing users with CRUD functionality and an intuitive API to manage their data.

---

## **Features**

### Lens Management
- **Create**: Add new lenses with details such as name, transmissions, and absorption spectrum.
- **Read**: Retrieve lenses by ID or list all available lenses in the database.
- **Update**: Modify existing lens records.
- **Delete**: Remove lens records securely.

### User Authentication
- Secure user registration and login with password hashing.
- Each lens record is associated with a user, ensuring data security and access control.

### Scalable APIs
- RESTful endpoints for seamless interaction with the system.
- Supports file uploads for bulk lens data processing.

### Database Integrity
- Designed with relational integrity between `users` and `lens` tables.
- Cascade delete ensures data consistency when users are removed.

---

## **Technologies Used**

### Backend
- **Java Spring Boot**:
  - Dependency Injection for modular and clean code.
  - RESTful API development.
  - Integration with PostgreSQL for data persistence.
  
- **PostgreSQL**:
  - Efficient and secure storage for lens and user data.
  - Foreign key constraints for relational integrity.

- **SQL**:
  - Schema design and query optimization.
  - Advanced use of constraints and cascades for security.

### Frontend
- **HTML, CSS, and JavaScript**:
  - User-friendly interface design.
  - Dynamic and interactive web components for better usability.

### Supporting Libraries
- **SLF4J**: For logging and monitoring.
- **Jackson**: For JSON processing.
- **JUnit** (planned): For testing and ensuring code quality.

## Key Design Choices

### **Lens Table**:
- Contains lens attributes, including:
  - `transmissions`: Comma-separated numerical values.
  - `absorption_spectrum`: Comma-separated numerical values.
    
---

## Skills Demonstrated

- **Java Development**: Implemented backend logic using Spring Boot with clean architecture principles.
- **SQL Expertise**: Designed relational database schemas and executed advanced queries.
- **Web Development**: Created user interfaces using HTML, CSS, and JavaScript.
- **RESTful API Design**: Enabled seamless interaction and scalability.
- **Data Validation**: Ensured data integrity during CRUD operations.
- **Secure Programming**: Implemented hashed password storage and role-based access control.

---

## Setup and Usage

### **Prerequisites**
- **Java** (JDK 11 or later).
- **Maven** for building the project.
- **PostgreSQL** as the database.

## Future Enhancements

- **Frontend Framework**:
  - Develop a modern UI with React or Angular for improved user experience.

- **Authentication Enhancements**:
  - Add JWT-based authentication for secure token-based management.

- **Data Analysis Tools**:
  - Integrate analytical tools for lens quality evaluation and visualization.

- **Unit Testing**:
  - Write comprehensive test cases with JUnit.

- **Cloud Deployment**:
  - Deploy the application on cloud platforms like AWS or Heroku.

---

## Conclusion

This project demonstrates proficiency in:

- **Backend Development**: Spring Boot implementation with clean architecture.
- **Database Management**: PostgreSQL for relational data handling.
- **Web Development**: Leveraging HTML, CSS, and JavaScript for frontend interfaces.
- **RESTful API Design**: Supporting CRUD operations with scalable architecture.

The Lens Repository Management System serves as a robust foundation for further development and highlights full-stack development capabilities.


