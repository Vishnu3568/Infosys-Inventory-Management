# InvenTrack - Infosys Inventory Management

This is a full-stack inventory system built for the Infosys Springboard Virtual Internship. It handles product tracking, stock management, and automated email alerts.

## Project Structure

The project is split into three main parts:
* **Auth Module (`inventory-authentication-module-main`)**: Handles login, signup, and roles (Admin/Staff/Supplier). Runs on port 8084.
* **Business Module (`IM-Business-Layer`)**: The core logic. Manages products, categories, and suppliers. It also sends out the low stock emails. Runs on port 8082.
* **Frontend (`inventory_project_presentation_layer`)**: The dashboard where you actually use the app. Runs on port 3000.

## Tech Stack

* **Backend**: Java 21, Spring Boot, MySQL, JPA, and JWT for auth.
* **Frontend**: React (built with Create React App).

## Quick Start (Development)

The easiest way to start all services locally is using the provided PowerShell script. This handles port cleanup and environment variable injection:

```powershell
.\run-all.ps1
```

## Module Structure & Ports

| Module | Directory | Port | Description |
|--------|-----------|------|-------------|
| **Auth** | `inventory-authentication-module-main` | 8084 | JWT Authentication & User Management |
| **Business** | `IM-Business-Layer` | 8082 | Core Logic, Product & Stock Management |
| **Frontend** | `inventory_project_presentation_layer` | 3000 | React Dashboard |

## Deployment & Manual Startup

When deploying or analyzing the project, follow this order:

1.  **Database**: Ensure MySQL is running and the `inventory_db` database is created.
2.  **Auth Service**:
    ```bash
    cd inventory-authentication-module-main
    mvn spring-boot:run
    ```
3.  **Business Service**:
    ```bash
    cd IM-Business-Layer
    mvn spring-boot:run
    ```
4.  **Frontend**:
    ```bash
    cd inventory_project_presentation_layer
    npm install
    npm start
    ```

## Environment Variables
The services rely on `.env` (or `.env.local` for frontend) files. Key variables include:
- `SPRING_DATASOURCE_URL`: JDBC connection string.
- `SPRING_DATASOURCE_USERNAME`/`PASSWORD`: DB credentials.
- `SERVER_PORT`: Port for the Spring Boot application.

---
*Internship Project - InvenTrack*
