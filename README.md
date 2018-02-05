## Name
Offer Service

## Description
Service responsible for handling offers for a merchant.  

The service does not contain details about **products** or **merchants**.  
It is developed with the assumption that there is another Product Service application responsible for managing products and the merchant has a administration console (other service) from where it handles products and offers.  
One way of functioning is: from the merchant administration console the operator selects a product for which to create an offer. When stored, the offer knows about the product id.  
When displaying the offers, additional details about the product of each offer can be retrieved by calling the Product Service.  

## Prerequisites
Application requires **Java 8** installed for running.  
Application requires **Maven 3** installed for development.  

## Package structure
Application consists of the following main packages:
+ `api` used for the API exposed by the application
+ `domain` used for domain model classes
+ `service` used for the service layer (business classes) 
+ `repository` used for the interaction with the storage system
+ `validation` contains classes used for validation

## Running from command line
Follow these steps to start the application:
+ run `mvn clean install`
+ run `mvn spring-boot:run` or `java -jar target/offer-service-0.0.1-SNAPSHOT.jar` to start the application

## Tests
Both unit tests and integration tests are stored with the project for ease of use.  
Ideally the integration tests should sit separately from the project.

### Unit tests
Unit tests can be run using maven `mvn clean test` or directly from the IDE.

### Integration tests
Integration tests following the naming convention `*TestIntegration` and are excluded from the build process.  
These tests have a separate maven profile. Run using `mvn clean install -P integration-tests` or from IDE.

## Database
**H2** is used for storage and it is automatically configured by Spring Boot.  
At startup, it is pre-populated with data from `data.sql` file.

## Logging
Currently not used, but needs to be added.

## API
The service exposes a REST API based on the JSON format.

### Create Offer
Endpoint
```
POST /offers
```
Request body
```json
{
    "productId": 15,
    "name": "The best offer ever",
    "description": "Some description",
    "enabled": true,
    "price": 20,
    "currency": "EUR",
    "startDate": "2018-01-01T14:00:00.000Z",
    "endDate": "2019-01-01T14:00:00.000Z"
}
```

### Update Offer
Endpoint
```
PUT /offers
```
Request body
```json
{
    "id": 1,
    "productId": 15,
    "name": "The best offer ever",
    "description": "Some description",
    "enabled": true,
    "price": 20,
    "currency": "EUR",
    "startDate": "2018-01-01T14:00:00.000Z",
    "endDate": "2019-01-01T14:00:00.000Z"
}
```

### Cancel Offer
Endpoint
```
PUT /offers/{offerId}/cancel
```

### Delete Offer
Endpoint
```
DELETE /offers/{offerId}
```

### Get Offer
Endpoint
```
GET /offers/{offerId}
```

### Get Offers
Endpoint (with default pagination)
```
GET /offers
```
Endpoint (with specified pagination)
```
GET /offers?page=0&size=20
```