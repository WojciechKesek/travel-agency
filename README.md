# ✈️ Travel Agency

A travel agency application designed to support the management of travel offers and provide a foundation for handling customers, reservations and travel-related operations.

The project is developed as a web application and is intended to provide a clean separation between the application's presentation, business and data-access layers.

---

## 📌 Features

The application is designed around the following travel-agency use cases:

* browsing available travel offers,
* managing travel destinations and offers,
* managing customer data,
* creating and managing reservations,
* retrieving travel information through the application API,
* persistent storage of application data,
* validation and error handling.

> The exact functionality depends on the current implementation of the project.

---

## 🛠️ Technology Stack

The project uses a backend/web application architecture.

Typical technologies used by the project include:

* Java
* Spring Boot
* Spring Web
* Spring Data / JPA
* Maven
* relational database
* REST API

---

## 📋 Requirements

Before running the project locally, make sure you have the required development tools installed.

### Required

* **JDK 25** or the Java version configured by the project
* **Maven 3.9+**
* **PostgreSQL** or the database configured by the application
* Git

Verify your installation:

```bash
java -version
mvn -version
git --version
```

---

## 🚀 Installation

### 1. Clone the repository

```bash
git clone https://github.com/WojciechKesek/travel-agency.git
cd travel-agency
```

### 2. Build the project

```bash
mvn clean install
```

### 3. Configure the application

Create or update the local application configuration with the required database and application settings.

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/travel_agency
spring.datasource.username=travel_agency
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
```

Do not commit passwords, API keys or other secrets to the repository.

---

## ▶️ Running the Application

Run the application using Maven:

```bash
mvn spring-boot:run
```

Alternatively, build the application and run the generated JAR:

```bash
mvn clean package
java -jar target/*.jar
```

By default, Spring Boot applications run on:

```text
http://localhost:8080
```

If another port is configured in the application, use that port instead.

---

## 🗄️ Database Configuration

The application requires a relational database for persistent storage.

### PostgreSQL example

Create the database:

```sql
CREATE DATABASE travel_agency;
```

Create a dedicated database user:

```sql
CREATE USER travel_agency WITH PASSWORD 'your_password';
```

Grant permissions:

```sql
GRANT ALL PRIVILEGES ON DATABASE travel_agency
TO travel_agency;
```

Configure the connection in `application.properties` or `application.yml`.

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/travel_agency
spring.datasource.username=travel_agency
spring.datasource.password=your_password
```

### Environment variables

For local and production environments, it is recommended to keep credentials outside the source code.

Example:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/travel_agency
export DB_USERNAME=travel_agency
export DB_PASSWORD=your_password
```

---

## 📁 Project Structure

A typical structure of the application is:

```text
travel-agency/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ...
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── repository/
│   │   │       ├── entity/
│   │   │       ├── dto/
│   │   │       └── config/
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       └── ...
│   │
│   └── test/
│       └── java/
│           └── ...
│
├── pom.xml
├── .gitignore
└── README.md
```

### Main layers

| Layer        | Responsibility                     |
| ------------ | ---------------------------------- |
| `controller` | REST API and HTTP request handling |
| `service`    | Business logic                     |
| `repository` | Database access                    |
| `entity`     | Database/domain entities           |
| `dto`        | Data transfer objects              |
| `config`     | Application configuration          |
| `test`       | Automated tests                    |

---

## 🔌 API

The application exposes functionality through HTTP endpoints.

The API is organized around the application's main domain objects, such as:

* travel offers,
* destinations,
* customers,
* reservations.

### Example API structure

```text
GET     /api/...
GET     /api/{id}
POST    /api/...
PUT     /api/{id}
PATCH   /api/{id}
DELETE  /api/{id}
```

> Replace the examples above with the exact routes implemented by the controllers in the project.

---

## 📡 Example Requests

### Get available travels

```bash
curl -X GET \
  http://localhost:8080/api/travels \
  -H "Accept: application/json"
```

Example response:

```json
[
  {
    "id": 1,
    "name": "Summer in Greece",
    "destination": "Greece",
    "price": 2499.99
  }
]
```

### Get a single travel

```bash
curl -X GET \
  http://localhost:8080/api/travels/1 \
  -H "Accept: application/json"
```

### Create a travel

```bash
curl -X POST \
  http://localhost:8080/api/travels \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Summer in Greece",
    "destination": "Greece",
    "price": 2499.99
  }'
```

### Update a travel

```bash
curl -X PUT \
  http://localhost:8080/api/travels/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Summer in Greece - Updated",
    "destination": "Greece",
    "price": 2699.99
  }'
```

### Delete a travel

```bash
curl -X DELETE \
  http://localhost:8080/api/travels/1
```

> The request examples above are illustrative. Adjust the endpoint paths and JSON fields to match the actual API implementation.

---

## 🧪 Testing

Run all tests:

```bash
mvn test
```

Run a clean test build:

```bash
mvn clean test
```

Run a specific test class:

```bash
mvn -Dtest=YourTestClass test
```

Run a specific test method:

```bash
mvn -Dtest=YourTestClass#yourTestMethod test
```

### Recommended test coverage

Tests should cover:

* business logic,
* REST controllers,
* repositories,
* validation,
* error handling,
* database integration,
* important application use cases.

---

## 🔍 Development

### Create a feature branch

```bash
git checkout -b feature/my-feature
```

### Make your changes

Implement the required functionality while following the existing project architecture and coding conventions.

### Run tests

```bash
mvn clean test
```

### Build the project

```bash
mvn clean package
```

### Commit your changes

```bash
git add .
git commit -m "Add my feature"
```

### Push your branch

```bash
git push origin feature/my-feature
```

Then open a Pull Request.

---

## 🤝 Contributing

Contributions are welcome.

Before creating a Pull Request:

* make sure the project builds successfully,
* make sure all tests pass,
* add tests for new functionality,
* follow the existing coding style,
* update documentation when API or configuration changes,
* avoid unnecessary changes outside the scope of the feature,
* never commit passwords, tokens or other sensitive information.

### Pull Request checklist

* [ ] Project builds successfully
* [ ] All tests pass
* [ ] New functionality is covered by tests
* [ ] API documentation has been updated if necessary
* [ ] Configuration changes have been documented
* [ ] No secrets have been committed
* [ ] Existing functionality has not been unintentionally broken

---

## 🐛 Issues

If you encounter a bug or have an idea for an improvement, open an issue in the repository:

https://github.com/WojciechKesek/travel-agency/issues

When reporting a bug, include:

1. Description of the problem
2. Steps to reproduce
3. Expected behavior
4. Actual behavior
5. Relevant logs or stack traces
6. Environment information

---

## 🔐 Security

Do not commit sensitive information to the repository.

This includes:

* database passwords,
* API keys,
* access tokens,
* private credentials,
* production configuration.

Use environment variables or a local configuration file for sensitive values.

---

## 📄 License

If this project contains a `LICENSE` file, refer to it for the applicable license and usage conditions.

---

## 👨‍💻 Development Notes

The project is intended to be developed incrementally. When adding new functionality, keep responsibilities separated between the API, business logic and persistence layers.

A good development flow is:

```text
HTTP Request
     │
     ▼
 Controller
     │
     ▼
 Service
     │
     ▼
 Repository
     │
     ▼
 Database
```

This structure makes the application easier to test, maintain and extend.
