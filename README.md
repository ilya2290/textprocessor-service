# Text Processing Service (Producer)

A **Spring Boot** service that fetches text from an external API and sends it to **Kafka** for further processing.  

---

## Tech Stack
- Java 17
- Maven
- Spring Boot 3.5
- Spring Kafka
- Spring Validation
- Spring WebFlux
- Lombok
- Apache Kafka
- Apache Zookeeper
- Docker

---

## Endpoints
- HTTP GET, Endpoint URL: /betvictor/text
- Returns the result of paragraph measures.
- Run endpoint to curl "http://localhost:8080/betvictor/text?p=3"
---

## Configuration

Main settings are located in `src/main/resources/application.properties`:

```properties
spring.application.name=textprocessor.service

server.port=8080

spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
spring.kafka.producer.properties.spring.json.add.type.headers=false

app.kafka-topic=words.processed
app.kafka-topic-partitions=3
app.kafka-topic-replication-factor=1

app.hipsum.base-url=https://hipsum.co
```

- Change the number of partition (by default - 3) ```app.kafka-topic-partitions=3``` 
- Change the replication factor (by default -1) ```app.kafka-topic-replication-factor=1```
---

## Docker Compose
Docker-compose file is located `src/main/java/com/textprocessor/service`

```
version: "3.8"

services:
  zookeeper:
    image: bitnami/zookeeper:3.9
    container_name: zookeeper
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
    ports:
      - "2181:2181"

  kafka:
    image: bitnami/kafka:3.6
    container_name: kafka
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      - KAFKA_BROKER_ID=1
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - KAFKA_CFG_LISTENERS=PLAINTEXT://:9092
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092
      - ALLOW_PLAINTEXT_LISTENER=yes

```

## Run the project
- docker-compose -p text_processor_service up -d
- mvn clean install
- mvn spring-boot:run


