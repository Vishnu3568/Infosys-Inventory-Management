# InvenTrack - My Inventory Management System

Hey there! This is **InvenTrack**, a full-stack project I built to handle all the common headaches of managing a warehouse or shop. It keeps track of products, manages stock levels, and even sends out emails when things are running low.

The whole thing is built using a microservices approach—meaning the login system and the actual inventory logic live in separate "brains" for better security and organization.

## 🔗 Live Link
Check it out here: [https://infosys-inventory-management.vercel.app](https://infosys-inventory-management.vercel.app)

---

## ⚡ What it does
*   **Login & Roles**: Secure login with JWT. Different views for Admins, Staff, and Suppliers.
*   **Dashboard**: A quick look at your total stock and what's running low.
*   **Smart Alerts**: The system automatically emails the admin when a product hits its "reorder level."
*   **Full Control**: Add, update, or deactivate products and categories easily.
*   **Search**: Fast search by name, category, or supplier.
*   **Mobile Friendly**: The dashboard works great on tablets and phones too.

---

## 🛠️ Tech I used

**Frontend:**
*   **React 18** for the UI.
*   **Vanilla CSS** (No heavy frameworks, just clean custom styles).
*   **Context API** to keep the user logged in across pages.

**Backend:**
*   **Java 21 & Spring Boot 3** (The latest and greatest).
*   **Spring Security + JWT** for the lock and key.
*   **Spring Data JPA** to talk to the database.
*   **Java Mail** for the automated alerts.

**Database & Hosting:**
*   **MySQL** for the data.
*   **Render** for hosting the backend APIs.
*   **Vercel** for the frontend.
*   **Aiven** for the cloud database.

---

## 🏗️ How it's structured
The project is split into three main parts:
1.  **Auth Module**: Handles all the user accounts and security tokens.
2.  **Business Layer**: This is where the actual inventory math and email alerts happen.
3.  **Frontend**: The React dashboard that ties it all together.

---

## 🚀 Setting it up yourself

If you want to run this on your own machine:

1.  **Get the code**:
    ```bash
    git clone https://github.com/Vishnu3568/Infosys-Inventory-Management.git
    ```
2.  **Database**:
    *   Create a MySQL database called `inventory_db`.
    *   Add your username/password in the `.env` files inside the backend folders.
3.  **Run it (Windows)**:
    I included a script called `run-all.ps1`. Just open PowerShell and run:
    ```powershell
    .\run-all.ps1
    ```
    This will start everything up at once.

---

## 📜 License
This project is under the MIT License. Feel free to use it!
