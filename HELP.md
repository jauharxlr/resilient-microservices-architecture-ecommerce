# Resilient Microservices Architecture

This project implements a resilient microservices architecture to handle order creation, payment processing, and user notifications. The architecture features fault-tolerance, observability, and automated API generation, ensuring modularity and scalability. 

---

## Notes:
Noting that this is a test project, I have shorthanded few of the implementations, and of course there are some icing and cherries as well:
1. There are no actual payment debit feature(ie, no actual payment gateway integration done).
2. There are no actual notification send(ie, no firebase or sms gateway integration).
3. Unit testing is only written for PaymentService.
4. I assume the project is fully capable of representing proper knowledge on API Documentation, resilience, Unit Testing, Error handling, Observability, etc...
5. Proper metrics are captured and pushed to prometheus using the OpenTelemtry's collector.
6. The project showcases extensive usage of gradle for source generation and custom build scripts.
7. The project also highlights the proper usage of docker.

### Services
1. **Order Service**: Manages customer orders and stores order details in an H2 database.
2. **Payment Service**: Processes payments for orders and triggers notifications.
3. **Notification Service**: Sends notifications (SMS or push) to customers' mobile numbers.

### Workflow
1. A customer initiates a request to the **Create Order API** of the **Order Service** from a client (e.g., frontend application).
2. The **Order Service** saves order details to the H2 database and calls the **Payment API** in the **Payment Service**.
3. The **Payment Service** processes the payment and calls the **Notification Service** to send a notification (SMS or push).

---

## Database Details

The **Order Service** uses an in-memory H2 database to store order details during runtime.

- **H2 Console URL**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL**: `jdbc:h2:mem:test`
- **Username**: `sa`
- **Password**: `password`

---

## API Documentation

OpenAPI documentation is provided for all microservices, enabling exploration and testing of APIs:

- **Order Service**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **Payment Service**: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)
- **Notification Service**: [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html)

---

## Resilience Implementation

### Patterns Used

1. **Circuit Breaker**:
    - Configured in the **Order Service** for the **Create Order API**.
    - Stops calls to the **Payment Service** temporarily when failures exceed a threshold.

2. **Retry Mechanism**:
    - Configured for calls from the **Order Service** to the **Payment API**.
    - Retries failed requests to handle transient errors.

3. **Timeout Mechanism**:
    - Configured for **Payment API** calls to terminate long-running requests.

These resilience patterns, implemented using **Resilience4j**, ensure system reliability, minimize downtime, and enhance the user experience.

- Similar approach is done in the **PaymentService** to ensure resilience when connecting with **NotificationService**    
---

## Observability

Observability is integrated into the system using **OpenTelemetry**.

### Distributed Tracing
- Trace details, including **Trace ID** and **Span ID**, are captured and pushed to **Zipkin** for end-to-end visibility.
- **Zipkin Dashboard URL**: [http://localhost:9411](http://localhost:9411)

### Metrics
- Metrics, such as request counts and response times, are collected and pushed to **Prometheus** for real-time monitoring.
- **Prometheus Dashboard URL**: [http://localhost:9090](http://localhost:9090)

---

## Automated API Management

This project uses **OpenAPI Generator** to simplify and standardize API-related tasks:

1. **API Documentation**: Automatically generates Swagger-based documentation for each service.
2. **REST API Clients**: Generates REST API clients to enable seamless inter-service communication.

By leveraging OpenAPI Generator, manual API client development is avoided, ensuring consistency and saving development time.

---

## Technologies Used

- **Spring Boot 3.3.4**: A Java framework for building microservices and web applications.
- **Resilience4j**: For implementing circuit breakers, retries, and timeouts to enhance system resilience.
- **OpenTelemetry**: Provides distributed tracing and metrics collection for observability.
- **Zipkin**: A distributed tracing system to visualize and monitor traces across services.
- **Prometheus**: A metrics monitoring system for real-time observability.
- **OpenAPI Generator**: Automates the generation of API documentation and REST API clients.
- **H2 Database**: An in-memory database for quick testing and prototyping.


## Setup

### Prerequisites
Ensure you have the following installed:
- **JDK 21 or higher**
- **Gradle**

### Steps to Run

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd <repository-folder>

