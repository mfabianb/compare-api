# Compare API (Spring Boot, Java 17, Gradle)

This is a sample RESTful API that provides product details designed for an item comparison feature.
It follows hexagonal architecture principles (ports & adapters), uses local JSON file persistence
(no database), supports pagination, filtering, HATEOAS links, and demonstrates Java inheritance/polymorphism
for product types.

## Features
- Java 17, Spring Boot 3.5.5, Gradle
- Hexagonal architecture: service port, repository port, file-based adapter
- Product polymorphism with `Product` base class and example subtypes (`ElectronicProduct`, `ClothingProduct`)
- REST endpoints with proper HTTP verbs and status codes
- Pagination (`page` 0-based, `size`) and filtering (`type`, `name`)
- HATEOAS links in responses
- Local JSON persistence: `src/main/resources/data/products.json`
- Error handling via `@ControllerAdvice`
- Tests using JUnit 5 and Mockito (example included)
- Swagger UI via springdoc-openapi at `/swagger-ui.html` (auto-configured)

## URI Examples
- `GET /api/v1/products` — list products (supports `page`, `size`, `type`, `name`)
- `GET /api/v1/products/{id}` — get a product (200 or 404)
- `POST /api/v1/products` — create (201 Created with Location header)
- `PUT /api/v1/products/{id}` — replace (200 or 404)
- `PATCH /api/v1/products/{id}` — partial update (200 or 404)
- `DELETE /api/v1/products/{id}` — delete (204 or 404)

## Pagination & Filtering
- Pagination is 0-based: `?page=0&size=10`
- Filter by type: `?type=ELECTRONIC`
- Search by name partial: `?name=phone`

## Error handling
- 400 Bad Request for invalid input and validation errors
- 404 Not Found when resource doesn't exist
- 500 Internal Server Error for unexpected failures

## Running
Build and run with Gradle:
```
./gradlew bootRun
```
The API will run on `http://localhost:8080`.

## Notes & Next steps
- The file-based repository is simplistic: consider moving to a DB for production.
- Mapping incoming DTOs to correct subtype should be improved (use factory by type).
- Add more comprehensive tests and CI configuration.
