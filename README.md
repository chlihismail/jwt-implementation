# jwt-implementation

## Project Overview
This project demonstrates a full-stack **JWT (JSON Web Token) implementation** using **Spring Boot**, **Spring Security**, and **ReactJS**. It showcases secure authentication and authorization in a web application. The application is containerized with **Docker Compose** and uses **MariaDB** as the database and **NGINX** as a reverse proxy.

---

## Features
- **User Authentication & Authorization** using JWT
- **Role-based access control** with Spring Security
- **Secure REST APIs** protected by JWT tokens
- **Frontend** built with ReactJS
- **Dockerized environment** with Docker Compose
- **MariaDB** as the relational database
- **NGINX** reverse proxy for frontend and backend routing
- Token expiration and refresh handling

---

## Technology Stack
| Layer | Technology |
|-------|------------|
| Backend | Spring Boot, Spring Security, JWT |
| Frontend | ReactJS |
| Database | MariaDB |
| Reverse Proxy | NGINX |
| Containerization | Docker, Docker Compose |

---

## Architecture

- ReactJS communicates with the backend through NGINX.
- Spring Boot handles authentication and generates JWT tokens.
- JWT tokens are used to access protected endpoints.
- MariaDB stores user credentials, roles, and other data.
- Docker Compose orchestrates all containers for easy deployment.

---

## Installation

### Prerequisites
- Docker & Docker Compose
- Java 17+
- Node.js & npm
