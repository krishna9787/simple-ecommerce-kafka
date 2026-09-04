# Simple E-Commerce Kafka Microservices

This repository demonstrates a small event-driven e-commerce workflow using Spring Boot and Kafka. The current implementation focuses on order creation and inventory validation, with asynchronous communication between services.

## Current Project State

The project currently contains three Spring Boot services:

1. Order Service
   - Path: `order-service/`
   - Stores orders in an H2 in-memory database
   - Exposes a REST endpoint for creating orders
   - Publishes a Kafka event to the `order-created` topic
   - Listens for `inventory-reserved` and `inventory-failed` events and updates the order by setting the event type/status

2. Inventory Service
   - Path: `inventory-service/`
   - Stores inventory data in an H2 in-memory database
   - Listens for `order-created` events
   - Reserves stock and produces either `inventory-reserved` or `inventory-failed`
   - Exposes an endpoint to add inventory records

3. Payment Service
   - Path: `payment-service/`
   - Project scaffold only
   - No business logic or Kafka integration has been implemented yet

## Technology Stack

- Java 17
- Spring Boot 4.1.0
- Spring Kafka
- Spring Data JPA
- H2 Database
- Maven
- Jackson for JSON serialization

## Service Ports

- Order service: `http://localhost:8081`
- Inventory service: `http://localhost:8082`
- Payment service: default Spring Boot port unless configured in its `application.yaml`

The H2 database consoles are enabled for the implemented services:

- Order service: `http://localhost:8081/h2-console`
- Inventory service: `http://localhost:8082/h2-console`

## Kafka Event Flow

The implementation currently follows this flow:

1. A client sends a request to `POST /api/order` in the order service.
2. `OrderServiceImpl.addOrder()` saves the order and publishes an `order-created` event with the order ID as the Kafka key.
3. `inventory-service` consumes the `order-created` event via `ConsumeOrderEvent`.
4. The inventory service validates stock availability and emits:
   - `inventory-reserved` when stock is available
   - `inventory-failed` when stock is insufficient
5. The order service consumes those inventory status events and updates the order record using `handleInventoryStatus()`.

## Kafka Topics in Use

- `order-created`
- `inventory-reserved`
- `inventory-failed`

## API Endpoints

### Order Service

Base URL: `http://localhost:8081`

- `POST /api/order`
  - Creates an order and publishes an event to Kafka.
  - Example request:

```json
{
  "eventId": "evt-001",
  "eventType": "ORDER_CREATED",
  "customerId": "CUST-1",
  "amount": 199.99,
  "orderId": "ORD-1001",
  "productId": "PROD-1",
  "quantity": 2
}
```

### Inventory Service

Base URL: `http://localhost:8082`

- `POST /api/inventory/add`
  - Adds an inventory record.
  - Example request:

```json
{
  "productId": "PROD-1",
  "availableQuantity": 25,
  "reservedQuantity": 0
}
```

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
├── .gitignore
└── .idea/
```

## Prerequisites

- JDK 17+
- Maven 3.6+
- Apache Kafka running locally on `localhost:9092`

## Running the Services

From the project root, start each service in a separate terminal:

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

- This project is currently focused on order fulfillment and inventory coordination using Kafka events.
- The payment service remains a scaffold and is not yet part of the implemented order-processing flow.
- Kafka bootstrap settings and service ports can be changed in each service's `application.yaml` file.

## Useful References

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Spring for Apache Kafka](https://spring.io/projects/spring-kafka)
