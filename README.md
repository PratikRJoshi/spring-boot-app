## Project Requirements Statement

This application must fulfill the following requirements:

### Functional Requirements

1. **Fetch All Posts**
   - Provide a REST API endpoint (`GET /api/getPosts`) that retrieves all posts from an external API (https://jsonplaceholder.typicode.com/posts) and returns them as an array of post objects.

2. **Fetch Post by ID**
   - Provide a REST API endpoint (`GET /api/getPostsById?id={id}`) that retrieves a single post by its ID from the external API.
   - Log incoming request headers for traceability.
   - Return the HTTP status code of the fetch operation.

3. **Create Payload**
   - Provide a REST API endpoint (`POST /api/createPayload`) that accepts a JSON payload with fields: `payload_id`, `kafka_topic`, and `created_at` (timestamp).
   - Validate that the `Content-Type` header is `application/json`.
   - Process and attempt to insert the payload (simulated logic).
   - Return HTTP status codes indicating success, bad request, or server error.

### Architectural Requirements

- The application must use a layered architecture with clear separation between controller, service, and model layers.
- The controller layer must handle HTTP requests and delegate to the service layer.
- The service layer must contain business logic, including external API calls and payload processing.
- The model layer must define data structures for posts and payloads.

### Kubernetes & Security Requirements

- **Kubernetes Deployment**: The application must be deployable on Kubernetes, using manifests for Deployments, Services, and Secrets. This enables scalable, portable, and manageable cloud-native operations.
- **Kafka Cluster**: The system must include a Kafka cluster, deployed and managed within Kubernetes, to support robust event streaming and messaging between services.
- **Spring Boot App as a Service**: The Spring Boot application must run as a Kubernetes Service, exposing its REST endpoints for internal or external access as needed.
- **NGINX API Gateway Layer**: An NGINX instance must be deployed as a reverse proxy in front of the Spring Boot service API endpoints. This is a best practice for handling routing, load balancing, SSL termination, and security in Kubernetes environments.
- **Digicert mTLS Authentication**: All service-to-service communication must use mutual TLS (mTLS) for strong security, with certificates managed by Digicert. This ensures both client and server authentication and encrypted traffic.

## Architecture Diagram

User / Client
    |
    |  HTTPS (mTLS)
    v
+-------------------+
|  NGINX API Gateway|  <--- Reverse proxy, mTLS
+-------------------+
           |
           |  Routes API requests
           v
+-------------------+
| Spring Boot App   |  <--- REST API, business logic
+-------------------+
           |
           |  Publishes/Consumes events
           v
+-------------------+
|   Kafka Broker    |  <--- Event streaming
+-------------------+

Spring Boot App <--- Fetches posts --- External API: https://jsonplaceholder.typicode.com/posts

All components (NGINX, Spring Boot, Kafka) run inside a Kubernetes Cluster for scalability and manageability.

---

   ![Architecture Diagram](architecture.svg)

## Project Capabilities

This Spring Boot application provides the following capabilities:

### REST API Endpoints

- **GET /api/getPosts**
  - Fetches all posts from an external API (https://jsonplaceholder.typicode.com/posts).
  - Returns an array of post objects.

- **GET /api/getPostsById?id={id}**
  - Fetches a single post by its ID from the external API.
  - Logs incoming request headers for traceability.
  - Returns the HTTP status code of the fetch operation.

- **POST /api/createPayload**
  - Accepts a JSON payload with fields: `payload_id`, `kafka_topic`, and `created_at` (timestamp).
  - Validates the `Content-Type` header (must be `application/json`).
  - Attempts to process and insert the payload (simulated logic).
  - Returns HTTP status codes indicating success, bad request, or server error.

### Internal Structure
- **Controller Layer**: Handles incoming HTTP requests and delegates to the service layer.
- **Service Layer**: Contains business logic, including external API calls and payload processing.
- **Model Layer**: Defines data structures for posts and payloads.

---

### Build the Project

1. Hava Java `v17.x` and Maven `v3.9.x` 
```
❯❯❯  java -version
openjdk version "17.0.7" 2023-04-18 LTS
OpenJDK Runtime Environment Zulu17.42+19-CA (build 17.0.7+7-LTS)
OpenJDK 64-Bit Server VM Zulu17.42+19-CA (build 17.0.7+7-LTS, mixed mode, sharing)

❯❯❯  mvn -v
Apache Maven 3.8.6 (84538c9988a25aec085021c365c560670ad80f63)
Maven home: /Users/pratik.joshi/Downloads/apache-maven-3.8.6
Java version: 1.8.0_332, vendor: Azul Systems, Inc., runtime: /Users/pratik.joshi/.sdkman/candidates/java/8.0.332-zulu/zulu-8.jdk/Contents/Home/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.16", arch: "x86_64", family: "mac"
```
2. `mvn  -s settings.xml clean install -U`
   Note: Remove the `-s` flag to use the `settings.xml` in ~/.m2
3. 

### Running the application
```
mvn spring-boot:run 
``` 


### Adding JVM Args for debugging
Add the `-Dspring-boot.run.jvmArguments` flag to the mvn command used for starting the app.
````
❯❯❯  mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005"
````

### Local Development & Testing Requirements

- **Developers must be able to build and run the entire stack locally using Docker Compose with a single command (e.g., `docker-compose up`).**
- **The Docker Compose setup should include:**
  - **Spring Boot application container**
  - **Kafka broker container**
  - **NGINX container as a reverse proxy**
- **All services should be networked together, and the NGINX proxy should route requests to the Spring Boot app.**
- **Documentation must be provided for building the Docker image and running the stack locally.**

## Learning Notes

Here are some markdown files with my learning notes:

- [Java Async Programming Timeline](learning/java_async_programming_timeline.md)
- [Reactive Programming](learning/reactive-programming-spring-summary.md)
- [SpringBoot Learnings](learning/springboot_learnings.md)
