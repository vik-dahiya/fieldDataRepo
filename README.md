# fieldDataRepo

# Manage a field from AGRO "https://agromonitoring.com" API
As an API consumer, CRUD operations on REST API, mappings are provided in the Controller with "/fields" root path.

# Manage a hard coded field
A Service layer has been defined to provide the business logic for CRUD operations on a hard coded Field. All mappings in Controller are provided for that with "/hard-coded".

# Testing the Application
Junit5 has been used to provide the automated testing of the application.

# Design Pattern
Spring MVC has been used as a framework to construct the application. Spring Boot with JDK 16 and Maven as build tool has been implemented for the application.

# Containerization
Application has been containerized using Docker. A Dockerfile is provided to provide an exceutable instance of the application.

# Exceution of Application locally using Docker Commands 
docker build -t field-data-api.jar . <br />
docker run -p 9900:9900 field-data-api.jar






