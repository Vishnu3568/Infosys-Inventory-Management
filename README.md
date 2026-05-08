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

## How to Run

### 1. Prerequisites
You'll need Java 21, Maven, Node.js, and a MySQL server running on `localhost:3306`.

### 2. Config
Check the `.env` files in each module folder to make sure your database and email settings are correct.

### 3. Startup (The Easy Way)
I've included a script that starts everything up for you. Just run this from the root:
```powershell
.\run-all.ps1
```

### 4. Manual Startup
If the script doesn't work for some reason, you can start them manually:

* **Auth**: `mvn -f "inventory-authentication-module-main/pom.xml" spring-boot:run`
* **Business**: `mvn -f "IM-Business-Layer/pom.xml" spring-boot:run`
* **Frontend**: `cd inventory_project_presentation_layer; npm start`

## Health Checks
If you want to see if the APIs are up:
* Auth: `http://localhost:8084/api/v1/public/health`
* Business: `http://localhost:8082/api/products`

---
*Internship Project - InvenTrack*
