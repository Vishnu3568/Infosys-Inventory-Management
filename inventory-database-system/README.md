# Inventory Management and Reporting System

A Spring Boot application providing the database layer for an Inventory Management and Reporting System.

> ⚠️ **This project is designed to run exclusively in IntelliJ IDEA.**

---

## Prerequisites

| Requirement       | Version  |
|--------------------|----------|
| **IntelliJ IDEA**  | 2024.1+  (Ultimate or Community) |
| **JDK**            | 21       |
| **MySQL**          | 8.0+     |
| **Maven**          | 3.9+ (bundled wrapper included) |

---

## Setup Instructions (IntelliJ IDEA)

### 1. Open the Project
- **File → Open** → Select the `database-system` folder
- IntelliJ will auto-detect the Maven project and import dependencies

### 2. Configure JDK
- **File → Project Structure → Project**
- Set **SDK** to JDK 21
- Set **Language Level** to 21

### 3. Configure MySQL Database
Make sure MySQL is running and create the database:
```sql
CREATE DATABASE IF NOT EXISTS inventory_db;
```

Update credentials in `src/main/resources/application.properties` if needed:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
spring.datasource.username=root
spring.datasource.password=2005
```

### 4. Enable Annotation Processing (for Lombok)
- **File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors**
- ✅ Check **Enable annotation processing**
- This is already pre-configured in the shared project settings

### 5. Run the Application
- Open the **Run** menu or press `Shift + F10`
- Select **DatabaseSystemApplication** from the run configurations
- The Spring Boot application will start on port **8081**

### 6. Verify
Open your browser and navigate to:
```
http://localhost:8081/actuator/health
```
You should see `{"status":"UP"}`.

---

## Project Structure

```
database-system/
├── .idea/                          # IntelliJ IDEA project config (shared)
│   ├── compiler.xml                # Annotation processing (Lombok)
│   ├── encodings.xml               # UTF-8 encoding
│   ├── jarRepositories.xml         # Maven repo config
│   ├── misc.xml                    # JDK 21 project settings
│   └── runConfigurations/
│       └── DatabaseSystemApplication.xml  # Spring Boot run config
├── src/
│   ├── main/
│   │   ├── java/com/inventory/database_system/
│   │   │   ├── DatabaseSystemApplication.java   # Entry point
│   │   │   ├── DataLoader.java                  # Sample data loader
│   │   │   ├── dto/                             # Data Transfer Objects
│   │   │   ├── entity/                          # JPA Entities
│   │   │   ├── enums/                           # Enumerations
│   │   │   ├── repository/                      # Spring Data Repositories
│   │   │   └── service/                         # Business logic services
│   │   └── resources/
│   │       └── application.properties           # App configuration
│   └── test/
├── pom.xml                         # Maven build config
├── mvnw / mvnw.cmd                 # Maven wrapper
└── README.md                       # This file
```

---

## Tech Stack

- **Java 21** — Language
- **Spring Boot 3.5** — Framework
- **Spring Data JPA** — Database access
- **Hibernate** — ORM
- **MySQL** — Database
- **Lombok** — Boilerplate reduction
- **Maven** — Build tool

---

## ⛔ Not Supported

This project does **not** support running from:
- VS Code
- Eclipse
- NetBeans
- Command line (`mvn spring-boot:run`)

Please use **IntelliJ IDEA** for development and execution.
