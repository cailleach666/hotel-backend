# iti0302-2024-backend



## Discription  
This is a simple Spring Boot application for hotel room bookings. 
The application allows users to search for available rooms and make bookings through a REST API.

## How to Run the Application

Here are 3 ways how you can run the application locally.

## Option 1: Run with Docker Compose (Recommended)

1. Clone the Repository:

    ```git clone <your repository URL>```

    ```cd <path to your project>```

2. Open the docker-compose.yml file and ensure the database credentials are correct:
    ```
    postgres:
    image: postgres
    container_name: hotel-db
    environment:
        POSTGRES_USER: admin
        POSTGRES_PASSWORD: password123
        POSTGRES_DB: hotel
    ```

3. Ensure the same credentials are reflected in the src/main/resources/application.properties file:

    ```spring.datasource.url=jdbc:postgresql://localhost:5432/hotel```

    ```spring.datasource.username=admin```

    ```spring.datasource.password=password123```

4. Start only the PostgreSQL container:
    
    ```docker-compose up postgres```

5. Verify that PostgreSQL is running:

    ```docker ps```

6. Now that the database is running, you can build the project:

    ```./gradlew build```


7. Once the JAR file is built successfully, you can build and run the entire application with Docker:

    ```docker compose up --build```

    The PostgreSQL service will run on localhost:5432.
    The Backend service will be available at http://localhost:8080.

8. Verify Database Connection:

    You can verify the PostgreSQL database is running correctly by connecting to it using a database client or running the following command:

    ```docker exec -it hotel-db psql -U admin -d hotel```


## Option 2: Run PostgreSQL in Docker, Backend Locally with Gradle

1. Ensure you have Java 21 and Gradle installed.  


2. Clone the repository:  

    ```git clone <your repository URL>```

    ```cd <path to your project>```

3. Open the docker-compose.yml file and ensure the database credentials are correct and the same credentials are reflected in the src/main/resources/application.properties file (**0ption 1**, 2-3 steps).

4. Start PostgreSQL Only:

    ```docker compose up postgres```

5. Verify the Database:

    You can verify that PostgreSQL is running by checking if the container is active:
    ```docker ps```

6. Now that the database is running, you can build the project:

    ```./gradlew build```

7. After building the project, you can go to src/main/java/BackendApplication.java and run it to start the application.

8. (Optional) Instead of doing steps 6-7, you can run the Backend Locally:

    ```./gradlew bootRun```

    The backend service will now be accessible at http://localhost:8080, connecting to the PostgreSQL instance running in Docker.


## Building the Application
To build the project, first ensure the database is running. You can either:

1. Start the database with Docker Compose:

    ```docker compose up postgres```

2. Then, build the project using Gradle:

    ```./gradlew build```

The built JAR file will be located in the build/libs directory.

## Running the Application as Docker Containers (for further development)

1. Build the Docker Image for the Backend:

    ```docker build -t hotel-backend:latest .```

2. Run the Backend Container:
    ```
    docker run -d -p 8080:8080 --name hotel-backend \
    -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/hotel \
    -e SPRING_DATASOURCE_USERNAME=admin \
    -e SPRING_DATASOURCE_PASSWORD=password123 \
    --network backend \
    hotel-backend:latest
    ```
