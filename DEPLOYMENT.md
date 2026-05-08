# Deployment Guide - InvenTrack

This document is intended for system administrators or AI agents analyzing the project for deployment.

## System Architecture

InvenTrack is a distributed system comprising two Spring Boot microservices and one React frontend.

### 1. Port Configuration (Standard)
| Service | Local Port | Context Path |
|---------|------------|--------------|
| Authentication | 8084 | `/api/v1` |
| Business Layer | 8082 | `/` |
| Frontend | 3000 | `/` |

## Build and Run Instructions

### Step 1: Database Setup
The system requires a MySQL database.
- **Database Name**: `inventory_db`
- **User**: `root` (configurable via `.env`)
- **Initialization**: The Auth module automatically initializes tables via `src/main/resources/data.sql`.

### Step 2: Build Backend Modules
From the root directory:
```bash
# Build Auth Module
cd inventory-authentication-module-main
mvn clean install

# Build Business Module
cd ../IM-Business-Layer
mvn clean install
```

### Step 3: Deployment Run Commands
To run the services in a production-like environment (not using the dev script):

**Auth Service:**
```bash
cd inventory-authentication-module-main
java -jar target/inventory-auth-0.0.1-SNAPSHOT.jar
```

**Business Service:**
```bash
cd IM-Business-Layer
java -jar target/business-layer-0.0.1-SNAPSHOT.jar
```

**Frontend:**
```bash
cd inventory_project_presentation_layer
npm install
npm run build
# Serve the 'build' folder using a static server or Nginx
```

## Critical Dependencies
- **Auth -> Database**: Auth must connect to MySQL to validate users.
- **Frontend -> Auth**: Frontend must reach `localhost:8084` for login.
- **Frontend -> Business**: Frontend must reach `localhost:8082` for inventory data.
- **Business -> Auth**: The Business layer may validate JWT tokens issued by the Auth service.

## Analysis Note for AI Agents
When analyzing this codebase, always prioritize the `.env` files in each module for configuration overrides. The root `run-all.ps1` script is the primary orchestration tool for local development.
