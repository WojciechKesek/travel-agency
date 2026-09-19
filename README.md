# ✈️ Travel Agency

A **Java Spring Boot REST API** for managing travel offers, customers and reservations.

The project was developed as a backend application with a layered architecture, focusing on **clean separation of responsibilities, persistence, validation, error handling and maintainable code**.

## 🎯 Project Overview

The application provides backend functionality for a travel agency, including:

* managing travel offers and destinations,
* managing customer data,
* creating and managing reservations,
* exposing functionality through REST APIs,
* persisting application data in a relational database,
* request validation and error handling.

The project demonstrates practical use of **Java, Spring Boot, Spring Data JPA and REST API development**.

## 👨‍💻 My Contribution

I developed the application and its backend functionality, including:

* implementing REST API endpoints,
* developing business logic and service-layer functionality,
* designing and implementing the persistence layer,
* working with Spring Data JPA and Hibernate,
* implementing validation and error handling,
* working with relational database persistence,
* writing and maintaining automated tests,
* structuring the application using separation of concerns and layered architecture.

The repository contains my development work and commits.

## 🏗️ Architecture

The application follows a layered architecture:

```text
                 HTTP Request
                      │
                      ▼
               ┌─────────────┐
               │ Controller  │
               └──────┬──────┘
                      │
                      ▼
               ┌─────────────┐
               │   Service   │
               └──────┬──────┘
                      │
                      ▼
               ┌─────────────┐
               │ Repository  │
               └──────┬──────┘
                      │
                      ▼
               ┌─────────────┐
               │  Database   │
               └─────────────┘
```

### Main layers

| Layer        | Responsibility                     |
| ------------ | ---------------------------------- |
| `controller` | REST API and HTTP request handling |
| `service`    | Business logic                     |
| `repository` | Data access                        |
| `entity`     | Domain/database entities           |
| `dto`        | Data transfer objects              |
| `config`     | Application configuration          |
| `test`       | Automated tests                    |

This structure keeps API, business logic and persistence responsibilities separated and makes the application easier to test and maintain.

## 🛠️ Technology Stack

### Backend

* **Java**
* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **Hibernate**
* **REST API**

### Database

* **PostgreSQL**
* **SQL**

### Build & Development

* **Maven**
* **Git**

### Testing

* **JUnit**
* automated unit/integration tests

## 📋 Requirements

Before running the project, make sure you have:

* **JDK 25** or the Java version configured in the project
* **Maven 3.9+**
* **PostgreSQL**
* **Git**

Verify your installation:

```bash
java -version
mvn -version
git --version
```

## 🚀 Running the Application

### 1. Clone the repository

```bash
git clone https://github.com/WojciechKesek/travel-agency.git
cd travel-agency
```

### 2. Configure the database

Create a PostgreSQL database and configure the connection in the application's configuration.

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/travel_agency
spring.datasource.username=travel_agency
spring.datasource.password=your_password
```

Keep credentials outside the repository and do not commit sensitive information.

### 3. Build the application

```bash
mvn clean install
```

### 4. Run the application

```bash
mvn spring-boot:run
```

Alternatively:

```bash
mvn clean package
java -jar target/*.jar
```

The application runs on the configured Spring Boot port, typically:

```text
http://localhost:8080
```

## 🗄️ Database

The application uses a relational database for persistent storage.

Example PostgreSQL setup:

```sql
CREATE DATABASE travel_agency;
```

Configure the database connection using the application's local configuration.

For local development, credentials should be supplied through configuration or environment variables rather than committed to source control.

## 🔌 REST API

The application exposes its functionality through REST endpoints related to the main domain objects, including:

* travel offers,
* destinations,
* customers,
* reservations.

The exact endpoints and request/response models are defined by the controllers implemented in the project.

## 🧪 Testing

Run the complete test suite:

```bash
mvn test
```

Or:

```bash
mvn clean test
```

The test suite covers application functionality such as business logic and API behaviour implemented in the project.

## 📁 Project Structure

```text
travel-agency/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ...
│   │   └── resources/
│   │       └── ...
│   │
│   └── test/
│       └── java/
│
├── pom.xml
├── .gitignore
└── README.md
```

## 💡 Engineering Practices

The project focuses on several backend development principles:

* separation of concerns,
* layered architecture,
* object-oriented design,
* maintainable code,
* validation,
* error handling,
* persistence abstraction,
* automated testing,
* keeping database credentials and secrets outside source control.

## 📚 What This Project Demonstrates

This project demonstrates practical experience with:

**Java → Spring Boot → REST API → Business Logic → JPA/Hibernate → PostgreSQL**

It also serves as a hands-on example of building and structuring a backend application using the Spring ecosystem.

---

## 🔗 Repository

[View the source code on GitHub](https://github.com/WojciechKesek/travel-agency)
