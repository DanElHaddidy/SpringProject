# SpringProject

Simple REST application with Spring Boot + Maven. 

This application sole purpose is to process REST Http requests and manage underlying customers list that is stored in DB.
Customer(resource) is simple POJO class with 3 fields (name, email, age).

### Resource:
```
{
    "name": "dan",
    "email": "danelhaddidy@gmail.com",
    "age": 26
}
```
## Architecture

Comunication between layers
(Controll layer <-> Service Layer <-> Model layer)

* **Controller Layer:** This layer is resposible to processing Htttp requests (GET, POST, PUT, DELETE, PATCH) and sending back appropriate responses.

* **Service Layer:** This layer is responsibel for business logic. Mainly checking for duplicity in DB. 
To prevent having two customers with same name/email. And to ensure customer is above the age of 18.  

* **Model layer:** This layer is using PostgreSQL (object-relational database system) for its DB that is running in Docker container.
All CRUD operations are done with the help of JPA premade methods + few custome made ones.

## Testing: 
All 3 layer are unit tested with the help of JUNIT 5, Mockito and more.
Controller layer unit is tested by sending requests and checking if the responses are correct. Using Postman and Unit testing.
Service layer is mainly checking if an appropriate exceptions are thrown when expected.
Model layer is checking if custome made methods are working as expected. Those operations are checked on a H2 Database as opposed to actual DB which is using PostgreSQL because H2 is swift.

**Technologies:** JAVA, Spring Boot, Docker, Postman, PostgreSQL, JPA, H2 Database, Maven, Gson, JUNIT 5, Mockito
