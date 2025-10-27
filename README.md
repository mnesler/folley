# folley
English topographic surname from Old French feuillie ("leafy bower or shelter; clump of trees")

## Spring Boot MVC Application

A Spring Boot 3.2.0 application with Java 17 demonstrating comprehensive JSON data handling and REST API design.

## Features

### Comprehensive Data Controller
Demonstrates handling of **all Java datatypes** in a single JSON payload:

- **Primitive Wrappers**: Byte, Short, Integer, Long, Float, Double, Boolean, Character
- **Strings**: String type
- **Big Numbers**: BigDecimal, BigInteger
- **UUID**: Universally Unique Identifier
- **Date/Time**: LocalDate, LocalTime, LocalDateTime, ZonedDateTime, Instant
- **Enums**: Custom enum types
- **Nested Objects**: Complex object hierarchies
- **Arrays**: String[], int[], Integer[]
- **Collections**: List, Set, Map
- **Null Values**: Nullable fields

### Product Controller
Basic CRUD operations for products with an intentionally failing test for TDD practice.

## Running the Application

```bash
./gradlew bootRun
```

## Testing

Run all tests:
```bash
./gradlew test
```

Run specific test:
```bash
./gradlew test --tests ProductControllerTest.testUpdateProduct_ShouldUpdateExistingProduct
```

## API Endpoints

### Data Controller

**GET** `/api/data/example` - Get example JSON with all datatypes

**POST** `/api/data/comprehensive` - Process comprehensive data

Example curl command:
```bash
curl -X POST http://localhost:8080/api/data/comprehensive \
  -H "Content-Type: application/json" \
  -d '{
  "byteValue": 127,
  "shortValue": 32767,
  "integerValue": 2147483647,
  "longValue": 9223372036854775807,
  "floatValue": 3.14159,
  "doubleValue": 2.718281828459045,
  "booleanValue": true,
  "characterValue": "A",
  "stringValue": "Hello, World!",
  "bigDecimalValue": "12345678901234567890.123456789",
  "bigIntegerValue": "999999999999999999999999999999",
  "uuidValue": "550e8400-e29b-41d4-a716-446655440000",
  "localDate": "2024-10-26",
  "localTime": "14:30:00",
  "localDateTime": "2024-10-26T14:30:00",
  "zonedDateTime": "2024-10-26T14:30:00-05:00[America/Chicago]",
  "instant": "2024-10-26T19:30:00Z",
  "status": "ACTIVE",
  "address": {
    "street": "123 Main St",
    "city": "Springfield",
    "state": "IL",
    "zipCode": "62701",
    "country": "USA"
  },
  "stringArray": ["one", "two", "three"],
  "intArray": [1, 2, 3, 4, 5],
  "integerArray": [10, 20, 30],
  "stringList": ["alpha", "beta", "gamma"],
  "integerSet": [100, 200, 300],
  "metadata": {
    "key1": "value1",
    "key2": 42,
    "key3": true
  },
  "addresses": [
    {
      "street": "456 Oak Ave",
      "city": "Portland",
      "state": "OR",
      "zipCode": "97201",
      "country": "USA"
    }
  ],
  "nullableField": null
}'
```

### Product Controller

**GET** `/api/products` - Get all products

**GET** `/api/products/{id}` - Get product by ID

**POST** `/api/products` - Create a new product

**PUT** `/api/products/{id}` - Update a product (currently has a bug for TDD practice)

**DELETE** `/api/products/{id}` - Delete a product

## Build

```bash
./gradlew build
```

## Requirements

- Java 17
- Gradle 8.5+
