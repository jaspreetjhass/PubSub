# Project Overview

This project is a microservices-based application built using Spring Boot and Spring Cloud. It demonstrates a common pattern for building resilient and scalable distributed systems. The services collaborate to provide various functionalities, including configuration management, service discovery, auditing, and business-specific tasks.

# Services

The project consists of the following microservices:

*   **`config-server`**:
    *   **Purpose**: Provides centralized configuration management for all other microservices. It uses Spring Cloud Config Server.
    *   **Technologies**: Spring Boot, Spring Cloud Config Server.
*   **`eureka-server`**:
    *   **Purpose**: Acts as a service registry where all other microservices register themselves and discover others. It uses Spring Cloud Netflix Eureka Server.
    *   **Technologies**: Spring Boot, Spring Cloud Netflix Eureka Server.
*   **`admintool-composite`**:
    *   **Purpose**: A composite service that likely provides administrative functionalities. It interacts with other services using Feign for declarative REST client communication and RabbitMQ for asynchronous messaging. It uses JPA for data persistence with an H2 in-memory database.
    *   **Technologies**: Spring Boot, Spring Cloud (Config Client, Eureka Client, OpenFeign), Spring AMQP (RabbitMQ), Spring Data JPA, H2, Swagger.
*   **`pubsub-audit-write`**:
    *   **Purpose**: Responsible for writing audit logs. It likely consumes messages from a Pub/Sub system (RabbitMQ in this case) and persists audit information using JPA with an H2 in-memory database.
    *   **Technologies**: Spring Boot, Spring Cloud (Config Client, Eureka Client), Spring AMQP (RabbitMQ), Spring Data JPA, H2, Swagger.
*   **`vision-services`**:
    *   **Purpose**: Provides vision-related functionalities through a REST API.
    *   **Technologies**: Spring Boot, Swagger, Lombok.

# Prerequisites

To build and run this project, you will need:

*   Java Development Kit (JDK) 8 or higher
*   Apache Maven

# Getting Started

## Building the Services

To build all the services, navigate to the root directory of each service and run the following Maven command:

```bash
mvn clean install
```

Alternatively, you can build them from their respective directories.

## Running the Services

Each service is a Spring Boot application and can be run using the following Maven command from its respective directory:

```bash
mvn spring-boot:run
```

Alternatively, after building, you can run the packaged JAR file:

```bash
java -jar target/<service-name>-0.0.1-SNAPSHOT.jar
```

Replace `<service-name>` with the actual name of the service (e.g., `config-server`, `eureka-server`).

### Recommended Startup Order

For the system to function correctly, it's recommended to start the services in the following order:

1.  **`config-server`**: As it provides configuration to other services.
2.  **`eureka-server`**: As it allows services to register and discover each other.
3.  **Other services**: `admintool-composite`, `pubsub-audit-write`, `vision-services` can be started after the above two are running.

# Database

Services like `admintool-composite` and `pubsub-audit-write` use Spring Data JPA and are configured to use an H2 in-memory database by default. This means data is not persisted between application restarts unless configured otherwise.

# API Documentation

Several services expose REST APIs and include Swagger for API documentation. Once a service is running, you can typically access its Swagger UI at the following URL:

```
http://localhost:<port>/swagger-ui.html
```

Replace `<port>` with the actual port number the service is running on (e.g., 8080, 8888, etc., check the application properties or console output for the exact port).

*   `admintool-composite`
*   `config-server`
*   `pubsub-audit-write`
*   `vision-services`

```
