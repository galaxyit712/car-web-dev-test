# ECS Cars Web Dev Test

# Cars API




# Solution
I have developed the solution using Java, SpringBoot APIs and Maven.

### 1- Technologies and Versions used:
| Technology | Version   |  
| ---------- | --------- |  
| JDK        | 1.8.0_251 | 
| Spring Boot| 2.6.1    |  
| h2 Database     | 1.4.200     |  
| Apache Maven     | 3.6.0     |  

## 2- Assumptions & Design Decisions
1. I have tried to follow the <strong>KISS</strong> and tried to <strong>not over-complicate</strong> the things. In my opinion, simple designs are best to start with. You can refactor the design and code as you adhere to more requirements and demands.     
2. Maximum number of silimar words extracted are five from https://api.datamuse.com.
3. In order to fulfil the requirement, _"As a Consumer of the API, I would like any cars I add through the API to persist between application restarts (persistent storage)"_. 
I  have written a special service as StateManager. This service maintains the data in a json between systems restarts. This is only available when system is started with "prod" file. In order to test this, I have used Spring-Actuator. In order to do graceful shutdown of the system, once can used simple command like **_curl -X POST http://localhost:8080/actuator/shutdown_** 

## REST APIs

    Add: /api/v1/cars/                        [POST]  
    Get specific car: /api/v1/cars/${id}      [GET]  
    Remove: /api/v1/cars/${id}                [DELETE]  
    Update: /api/v1/cars/${id}                [PUT]  

## Persistence
For this test, I have used <strong>H2</strong> in memory database.

###Test Coverage
100% test coverage

###To shutdown the system 
From commandline, use following command from cmdline**
######curl -X POST http://localhost:8080/actuator/shutdown

##How to test/run the system:
1. From Intellij or any similar editor - Import the project as Java project. <strong>CarsLaunchPad</string> with program arguments as --spring.profiles.active=prod. You can use a tool like Postman or Insomnia to test the Rest URLs.
The URLs may look like

    1. To save a car, <url>http://localhost:8080/api/v1/cars/<url>, POST method with JSON body of car.   
    2. To fetch a specific car, <url>http://localhost:8080/api/v1/cars/1</url>, Get method with ID of the car in Path variable
    3. To update a specific car, <url>http://localhost:8080/api/v1/cars/1</url>, PUT method with with ID of the car in Path variable and JSON body of car.
    4. To delete a specific car, <url>http://localhost:8080/api/v1/cars/1</url>, DELETE method with ID of the car in Path variable
    
2. From outside of intellij, please follow instructions "Packaging and running"

##Packaging and running
 The application can be packaged with following command
 
     ./mvnw package
     
  This will produce the file car-web-dev-test.jar in target folder of the source directory.
  
  Launch the application using following command
     java -jar target/car-web-dev-test-0.0.1-SNAPSHOT.jar 
  
  You can now fire the REST urls to leverage the services.