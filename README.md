# Simple E-Commerce Kafka Microservices

This repository contains a Spring Boot-based microservices sample for an e-commerce workflow, with asynchronous communication handled through Kafka.

## Current Project State

The codebase currently contains the following services:

1. Inventory Service
   - Path: `inventory-service/`
   - Uses H2 in-memory database and Kafka producer/consumer configuration
   - Consumes `order-created` messages
   - Reserves stock and emits `inventory-reserved` or `inventory-failed`

2. Order Service
   - Path: `order-service/`
   - Uses H2 in-memory database and Kafka producer/consumer configuration
   - Exposes a REST endpoint for creating orders
   - Publishes `order-created` events when an order is created
   - Consumes `inventory-reserved` messages to update order state

3. Payment Service
   - Path: `payment-service/`
   - Scaffolded Spring Boot project, but it is not yet fully implemented in the current codebase
   - No business logic or event consumers are configured yet

## Technology Stack

- Java 17
- Spring Boot 4.1.0
- Apache Kafka
- Spring Data JPA
- H2 Database
- Maven

## Service Ports and Configuration

The services are currently configured with the following ports:

- `order-service`: `http://localhost:8081`
- `inventory-service`: `http://localhost:8082`
- `payment-service`: uses the default Spring Boot port unless configured in its `application.yaml`

The in-memory H2 database consoles are available at:

- `http://localhost:8081/h2-console`
- `http://localhost:8082/h2-console`

## Kafka Event Flow

The current event-driven flow implemented in the application is:

1. `order-service` creates an order through REST
2. `order-service` publishes message to Kafka topic `order-created`
3. `inventory-service` listens to `order-created`
4. `inventory-service` validates stock and publishes either:
   - `inventory-reserved`
   - `inventory-failed`
5. `order-service` listens to `inventory-reserved` and updates the order status

## API Endpoints

### Order Service

Base URL: `http://localhost:8081`

- `POST /api/order`
  - Creates a new order
  - Example request body:

```json
{
  "orderId": "ORD-1001",
  "customerId": "CUST-1",
  "productId": "PROD-1",
  "quantity": 2,
  "status": "PENDING"
}
```

### Inventory Service

Base URL: `http://localhost:8082`

- `POST /api/inventory/add`
  - Adds inventory for a product
  - Example request body:

```json
{
  "productId": "PROD-1",
  "quantity": 25,
  "price": 199.99
}
```

## Kafka Topics in Use

The following topics are currently wired in the code:

- `order-created`
- `inventory-reserved`
- `inventory-failed`

## Project Structure

```text
simple-ecommerce-kafka/
├── inventory-service/
│   ├── src/
│   ├── pom.xml
│   ├── mvnw
│   └── HELP.md
├── order-service/
│   ├── src/
│   ├── pom.xml
│   ├── mvnw
│   └── HELP.md
├── payment-service/
│   ├── src/
│   ├── pom.xml
│   ├── mvnw
│   └── HELP.md
├── README.md
└── .gitignore
```

## Prerequisites

- JDK 17+
- Maven 3.6+
- Apache Kafka running locally on `localhost:9092`

## Running the Services

From the project root, run each service individually:

```bash
cd inventory-service
./mvnw spring-boot:run
```

```bash
cd order-service
./mvnw spring-boot:run
```

```bash
cd payment-service
./mvnw spring-boot:run
```

## Building and Testing

```bash
cd inventory-service && ./mvnw test
cd order-service && ./mvnw test
cd payment-service && ./mvnw test
```

## Notes

- This project is currently focused on inventory validation for order processing and demonstrates a real Kafka-based event-driven workflow.
- The payment service is a placeholder and will need additional implementation for payment processing and payment event integration.
- Some configuration values, such as the Kafka broker and service ports, can be updated in each service's `application.yaml` file.

## Useful References

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Spring for Apache Kafka](https://spring.io/projects/spring-kafka)
