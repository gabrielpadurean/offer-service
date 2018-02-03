## Name
Offer Service

## Description
Service responsible for handling offers for a merchant.

## Prerequisites
Application requires **Java 8** installed for running.  
Application requires **Maven 3** installed for development.  

## Package structure
Application consists of the following main packages:
+ `api` used for the API exposed by the application
+ `domain` used for domain model classes
+ `service` used for the service layer (business classes) 
+ `dao` used for the interaction with the storage system

## Running from command line
Follow these steps to start the application:
+ run `mvn clean install`
+ run `mvn spring-boot:run` or `java -jar target/offer-service-0.0.1-SNAPSHOT.jar` to start the application