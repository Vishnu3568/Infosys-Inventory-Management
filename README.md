# Infosys Inventory Management - Local Integration Guide

This workspace contains a multi-module inventory system:
- Auth service: `inventory-authentication-module-main` (port `8084`)
- Business service: `IM-Business-Layer/server` (port `8082`)
- Frontend: `inventory_project_presentation_layer` (port `3000`)

## Prerequisites

- Java 21
- Maven 3.9+
- Node.js 18+
- MySQL running on `localhost:3306`

## Environment

Configured local defaults:
- Frontend API URLs in `inventory_project_presentation_layer/.env.local`
- Auth env in `inventory-authentication-module-main/.env`
- Business env in `IM-Business-Layer/server/.env`

CORS is configurable via env vars:
- `APP_CORS_ORIGIN` (single origin)
- `APP_CORS_ORIGINS` (comma-separated list for auth security config)

## Recommended startup (safe)

From repository root:

```powershell
.\run-all.ps1 -SkipDatabaseSystem
```

This starts auth + business + frontend in separate PowerShell windows and avoids duplicate-run conflicts.

Optional:

```powershell
.\run-all.ps1 -SkipDatabaseSystem -SkipFrontend
```

## Manual startup (if needed)

Run each service once in separate terminals:

```powershell
mvn -f "c:/Users/uvish/Infosys-Inventory-Management/inventory-authentication-module-main/pom.xml" spring-boot:run
```

```powershell
mvn -f "c:/Users/uvish/Infosys-Inventory-Management/IM-Business-Layer/server/pom.xml" spring-boot:run
```

```powershell
cd c:/Users/uvish/Infosys-Inventory-Management/inventory_project_presentation_layer
npm start
```

## Health checks

```powershell
Invoke-RestMethod -Uri "http://localhost:8084/api/v1/public/health" -Method Get
Invoke-WebRequest -Uri "http://localhost:8082/api/products" -Method Get -UseBasicParsing
```

## Common issue

If you see Maven `spring-boot:run` exit `-1` after a while, it usually means the process was terminated externally or duplicate service instances were started. Use the launcher script (`run-all.ps1`) and avoid starting the same module multiple times in parallel.
