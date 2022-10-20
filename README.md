# iCommerce backend

## Introduction
iCommerce backend is a proposed MVP version for an online shopping application. This version includes the following capabilities:
- Clients can search for products based on some specified conditions, and view a product's details.
- Clients can add a product to an existing or new shopping cart.
- Clients can update amount of existing products in shopping cart. Or they can remove any existing item from the cart.
- Clients can perform checkout, so the order will get processed by the suppliers
- Authentication/Authorization is not necessary in this version. The customers can anonymously create a cart, checkout with required information like name, address, phone number... The FE client will provide mechanism to store cart information (e.g. in local storage / cookies) so they can resume the shopping with ease.

### Applied principles and patterns
- **SOLID** principles
  - Endpoints, classes, methods, models are designed such that they have only one responsibility and should only be changed when it needs to be changed. 
  - Most contracts are interface-based, and implementation changes should not make any impacts to existing behaviors. They can also be extended with additional behaviors easily.
  - Dependency injections are applied all over the project, specifically constructor injections.
- Defensive programming:
  - Inputs should be carefully checked
  - And errors should be gracefully handled
- TDD: 
  - Organize classes, and methods in an easy way to test.
  - Currently, I do prefer unit testings over integration tests, due to the fact that the project is relative simple, and we haven't had many associated modules or components

## Project structures
### Libraries and frameworks
- Spring Boot
- Hibernate validator: for easier bean validations
- Spring Data JPA: provide hands on API that can be used easily to work with underlying database
- Mapstruct: mapping from DTO to Entity and vice versa
- QueryDSL: help to make complex queries easier and typesafe
- Liquibase: to initiate database and manage migrations
- Lombok: avoid boilerplate code
- JUnit5, Mockito: For unit testings

### Folder structure
The classes are separated into 4 main packages:
- presentation: this is the closes layer to the clients
  - controller: represents endpoints which is exposed for client to access
  - request: DTOs which originate from client side
  - response: DTOs that are returned to clients
  - utils: necessary utils
- domain: provide domain layer classes, including:
  - entity: entities of project
  - type: enums and other types which is used for 
  - exception: define exceptions 
- repository: provide persistence or external data access / communications
  - converter: helpers that provide converting custom data types to database column and vice versa
  - persistence: contains access to persistence storage
- service: application service that bridge domain and presentation layer
  - mapper: mapping data from requests to entity, and entity to response


## Project setup
To run this project on local computer, the following software are required:
- Java version 11

There are a couple of methods to run this project locally:
- Using maven:
```shell
./mvnw clean compile
./mvnw spring-boot:run
```

- Or use your favorite IDE.
- To use preconfigured information for local runs, use `local` profile. E.g:
```shell
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```
- To init database with sample data, use `init-sample` profile
```shell
./mvnw spring-boot:run -Dspring-boot.run.profiles=local,init-sample
```

- The default implementation uses H2DB with in-memory database. If you prefer another database (like PostgresQL), please update necessary information in `application-local.properties`.
- To execute unit tests, run this command
```shell
./mvnw clean test
```

## API verification
Hint: If you use IntelliJ IDEA as your IDE, you can execute the sample requests in `requests.http`. 
### Search products
```shell
curl -X GET --location "http://localhost:8080/product?page=0&limit=5&name=shir&brand=Uniqlo&minPrice=210000&maxPrice=220000&category=1&color=red"
```

Example response
```json
{
  "content": [
    {
      "id": 5,
      "name": "Shirt A1",
      "price": 220000.0,
      "sku": "S00A1R",
      "color": "red",
      "brand": "Uniqlo",
      "category": {
        "id": 4,
        "name": "clothing"
      }
    }
  ],
  "pageable": {
    "sort": {
      "sorted": false,
      "unsorted": true,
      "empty": true
    },
    "pageNumber": 0,
    "pageSize": 5,
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 1,
  "last": true,
  "first": true,
  "sort": {
    "sorted": false,
    "unsorted": true,
    "empty": true
  },
  "number": 0,
  "numberOfElements": 1,
  "size": 5,
  "empty": false
}
```

### Get a product detail
```shell
curl -X GET --location "http://localhost:8080/product/{id}"
```

Example output:
```json
{
  "id": 4,
  "name": "Shirt A1",
  "price": 200000.0,
  "sku": "S00A1W",
  "color": "white",
  "brand": "Uniqlo",
  "category": {
    "id": 4,
    "name": "clothing"
  }
}
```

### Add a product to shopping cart (new cart)
```shell
curl -X POST --location "http://localhost:8080/cart" \
    -H "Content-Type: application/json" \
    -d "{
          \"productId\": 2,
          \"amount\": 1
        }"
```

Example request in JSON:
```json
{
  "productId": 2,
  "amount": 1
}
```

Example response: 
```json
{
  "cartId": "47264c82-bb4e-4f10-b337-e793c9e669f7",
  "status": "NEW",
  "products": [
    {
      "product": {
        "id": 4,
        "name": "Shirt A1",
        "price": 200000.0,
        "sku": "S00A1W",
        "color": "white",
        "brand": "Uniqlo",
        "category": null
      },
      "amount": 1
    }
  ],
  "costSummary": {
    "totalCost": 200000.0
  }
}
```

### Add a product to existing cart
```shell
curl -X POST --location "http://localhost:8080/cart" \
    -H "Content-Type: application/json" \
    -d "{
          \"cartId\": \"cartId\",
          \"productId\": 2,
          \"amount\": 1
        }"
```

Example request in JSON:
```json
{
  "cartId": "049e03e0-8e96-42ff-9f13-c1db45666d16",
  "productId": 2,
  "amount": 1
}
```

### Update an existing product in cart
```shell
curl -X PUT --location "http://localhost:8080/cart/{cartId}" \
    -H "Content-Type: application/json" \
    -d "{
          \"items\": [
            {
              \"productId\": 1,
              \"amount\": 4
            }
          ]
        }"
```
Example request in JSON:
```json
{
  "items": [
    {
      "productId": 1,
      "amount": 4
    }
  ]
}
```

### Get details of cart
```shell
curl -X GET --location "http://localhost:8080/cart/{cartId}
```

Example response:
```json
{
  "cartId": "47264c82-bb4e-4f10-b337-e793c9e669f7",
  "status": "NEW",
  "products": [
    {
      "product": {
        "id": 4,
        "name": "Shirt A1",
        "price": 200000.0,
        "sku": "S00A1W",
        "color": "white",
        "brand": "Uniqlo",
        "category": null
      },
      "amount": 1
    }
  ],
  "costSummary": {
    "totalCost": 200000.0
  }
}
```

### Checkout cart
```shell
curl -X POST --location "http://localhost:8080/cart/checkout/{cartId}" \
    -H "Content-Type: application/json" \
    -d "{
          \"customerName\": \"Vinh Truong\",
          \"address\": \"HCM\",
          \"phoneNumber\": \"0776703343\",
          \"paymentMethod\": \"CASH\"
        }"
```

Example request in JSON:
```json
{
  "customerName": "Vinh Truong",
  "address": "HCM",
  "phoneNumber": "0776703343",
  "paymentMethod": "CASH"
}
```

Example response:
```json
{
  "id": "ca6e7e5b-d23a-4954-ab62-2c71126536a5",
  "createdDate": "2022-10-21T00:00:16.279263119+07:00",
  "amount": 200000.0,
  "customerName": "Vinh Truong",
  "address": "HCM",
  "phoneNumber": "0776703343"
}
```
