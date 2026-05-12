# Inventory Management and Monitoring System

Welcome to my **Inventory Management and Monitoring System**! I built this project to solve a real-world problem: how to keep track of a massive amount of stock without losing your mind. It’s not just a database; it’s a smart system that actually *monitors* your inventory and tells you when to take action.

## 🔗 Live Link
Try it out live here: [https://infosys-inventory-management.vercel.app](https://infosys-inventory-management.vercel.app)

---

## 🤔 Why I built this?
Managing stock manually is a nightmare. I wanted to create a tool where you could see everything in one glance. I chose a **Microservices Architecture** because I wanted the authentication and the business logic to be completely decoupled. This makes the app more secure and easier to scale up later on.

## 🚀 Key Features
*   **Smart Monitoring**: The system doesn't just sit there. It watches your stock levels. If something drops below the "reorder level," it triggers an alert.
*   **Automated Email Alerts**: I integrated Java Mail so that the system can automatically ping the admin when stock is getting dangerously low.
*   **Stateless Authentication**: I used JWT (JSON Web Tokens) so the app stays secure without needing a constant session connection.
*   **Role-Based Access**: There are three levels: **Admin** (full power), **Staff** (can manage stock), and **Suppliers** (can see what's needed).
*   **Clean Dashboard**: A custom-styled React dashboard that gives you the "big picture" (Total Stock, Categories, Low Stock Items) immediately.

---

## 🛠️ The Tech Stack

**Frontend (The "Face"):**
*   **React 18** (Functional components and Hooks).
*   **Context API** for global state management.
*   **Vanilla CSS** for all the styling (I wanted full control over the look and feel).

**Backend (The "Brain"):**
*   **Java 21 & Spring Boot 3**.
*   **Spring Security + JWT** for the security layer.
*   **Hibernate / Spring Data JPA** for smooth database operations.
*   **Java Mail Sender** for the SMTP alerts.

**Infrastructure:**
*   **MySQL** (Relational data).
*   **Render** (Backend Hosting).
*   **Vercel** (Frontend Hosting).
*   **Aiven** (Cloud Database).

---

## 📁 How the Code is Organized
The project is split into three main modules:

*   **`inventory-authentication-module-main`**: This is the security guard. It handles logins, signups, and issues tokens.
*   **`IM-Business-Layer`**: This is the core engine. It handles all the product math, category logic, and the monitoring service that sends emails.
*   **`inventory_project_presentation_layer`**: This is the React app. It’s the bridge between the user and the two backend services.

---

## 🧠 Challenges I Faced
*   **Cross-Origin (CORS)**: Deploying microservices on different platforms (Render and Vercel) was tricky. I had to configure the backends to trust the dynamic Vercel subdomains using `allowedOriginPatterns`.
*   **Service Communication**: Making sure the frontend knew exactly when to talk to the Auth API versus the Business API took some careful environment variable mapping.
*   **Database Synchronization**: Using a cloud database (Aiven) required me to ensure the JDBC connections were secure and reliable across different hosting regions.

---

## 🚀 How to Run it Locally

1.  **Clone it**:
    ```bash
    git clone https://github.com/Vishnu3568/Infosys-Inventory-Management.git
    ```
2.  **Database Setup**:
    *   Set up a MySQL DB called `inventory_db`.
    *   Update the `.env` files in the backend folders with your local DB credentials.
3.  **One-Click Start**:
    If you're on Windows, I wrote a PowerShell script to make your life easier. Just run:
    ```powershell
    .\run-all.ps1
    ```

---

## 🛣️ Future Roadmap
*   [ ] Add a "Scan Barcode" feature for mobile users.
*   [ ] Integrate a graphical chart (like Chart.js) for stock trends over time.
*   [ ] Add PDF export for monthly inventory reports.

---

## 📜 License
This project is licensed under the MIT License—feel free to use it and build upon it!
