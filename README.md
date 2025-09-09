# Compare API (Spring Boot, Java 17, Gradle)

This is a sample RESTful API that provides product details designed for an item comparison feature.
It follows hexagonal architecture principles (ports & adapters), uses local JSON file persistence
(no database), supports pagination, filtering and demonstrates Java inheritance/polymorphism
for product types.

## Features
- Java 17, Spring Boot 3.5.5, Gradle
- Hexagonal architecture: service port, repository port, file-based adapter
- Product polymorphism with `Product` base class and example subtypes (`ElectronicProduct`, `ClothingProduct`)
- REST endpoints with proper HTTP verbs and status codes
- Pagination (`page` 0-based, `size`) and filtering (`type`, `name`)
- Local JSON persistence: `src/main/resources/data/products.json`
- Error handling via `@ControllerAdvice`
- Tests using JUnit 5 and Mockito (example included)
- Swagger UI via springdoc-openapi at `/swagger-ui.html` (auto-configured)
- Run And Deploy using Docker

## Run and deploy
### Run commands
```
docker build . -t compare-api
```

```
docker run -p 8080:8080 compare-api
```

The API will run on `http://localhost:8080`.

## URI Examples
- `GET /api/v1/products` — list products (supports `page`, `size`, `type`, `name`)
- `GET /api/v1/products/{id}` — get a product (200 or 404)
- `POST /api/v1/products` — create (201 Created with Location header)
- `PUT /api/v1/products/{id}` — replace (200 or 404)
- `PATCH /api/v1/products/{id}` — partial update (200 or 404)
- `DELETE /api/v1/products/{id}` — delete (204 or 404)

### Product Comparison
| Method | Endpoint | Params | Description |
|--------|-----------|--------|-------------|
| `GET`  | `/api/products/compare` | `ids` (required), `fields` (optional) | Compare multiple products by ID. If `fields` is provided, only those fields are returned. |

Example:
```
GET /api/products/compare?ids=UUID1,UUID2&fields=name,price,rating
```

You can use
```
UUID Product 1: 198e8930-f445-493c-9b28-65bce08f7b43
```

```
UUID Product 2: 379a0f5f-e355-46d5-895b-9e1dfe276106
```

---

## 📖 Swagger UI
API documentation available at:  
👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## ⚡ Example Requests

### Compare Products
```http
GET http://localhost:8080/api/products/compare?ids=UUID1,UUID2
```

### Create Product
POST /api/products
```json
{
  "id": "7945cc8c-bed2-4e9b-94f8-0c9319427b10",
  "type": "BOOK",
  "name": "Clean Code",
  "description": "A Handbook of Agile Software Craftsmanship",
  "price": 29.99,
  "rating": 4.9,
  "specifications": {
    "author": "Robert C. Martin",
    "pages": "464"
  },
  "imageUrl": "https://example.com/images/cleancode.jpg"
}
```

## Pagination & Filtering
- Pagination is 0-based: `?page=0&size=10`
- Filter by type: `?type=ELECTRONIC`
- Search by name partial: `?name=phone`

## Error handling
- 400 Bad Request for invalid input and validation errors
- 404 Not Found when resource doesn't exist
- 500 Internal Server Error for unexpected failures




## Notes & Next steps
- The file-based repository is simplistic: consider moving to a DB for production.
- Mapping incoming DTOs to correct subtype should be improved (use factory by type).
- Add more comprehensive tests and CI configuration.
