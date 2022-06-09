# mintyn-inventory-report-management

This is a Sales/Inventory-Report Distributed system Application

It is built with the combination of both the Event Driven Microservices Architecture and
the Service Oriented Architecture.

### Technologies

- Java
- Maven
- Kafka
- Docker
- Apache Poi

### Requirements

You need the following to build and run the application:

- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 3.8.1](https://maven.apache.org) (This is the build tool used.)
- [Docker](https://www.docker.com/products/docker-desktop/)

## How to run Application(s)
### step 1 - clone project with Terminal from [here](https://github.com/foyiogu/mintyn-inventory-report-management.git)

```
git clone https://github.com/foyiogu/mintyn-inventory-report-management.git
```

### step 2 - move into the project directory
```
cd mintyn-inventory-report-management/
```

### step 3 - Open the mintyn-inventory-report-management Folder in an IDE, As a maven Project.

### step 4 - Generate the .jar files with Terminal

```
mvn clean install 
```
OR
```
./mvnw clean install
```

### step 5 - Once Docker is Open and connected, Get the Images for all external programs used - Postgres, PGAdmin, Zookeeper, Kafka, by running in Terminal:
```
docker-compose up -d
```
OR
```
docker compose up -d
```

### step 5 - Run The OrderReportApplication and the SalesInventoryApplication with The IDE

## Swagger Documentation Can Be Found Below
- For Sales/Inventory [here](http://localhost:8080/swagger-ui/)
- For Order Report [here](http://localhost:8081/swagger-ui/)

## When the order report endpoint is used, An Excel Spreadsheet is Generated for the report view

## Running The Tests with Maven

To run the tests with maven, you would need to run the maven command for testing, after the code has been compiled.
```
mvn <option> test
```
- If "mvn" doesn't work, please use "./mvnw".

#### ReportService Test
- All Tests in ReportServiceImplTest can be run using
```
mvn -Dtest=ReportServiceImplTest test
```

#### OrderService Test
- All Tests in OrderServiceImplTest can be run using
```
mvn -Dtest=OrderServiceImplTest test
```

#### ProductService Test
- All Tests in ProductServiceImplTest can be run using
```
mvn -Dtest=ProductServiceImplTest test
```
