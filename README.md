# e-commerce sample app

## How to run this sample

In Linux/OSX run:
```
./mvnw spring-boot:run
```

In Windows platform run:
```
mvnw.cmd spring-boot:run
```

## Database configuration

This project uses an in-memory H2 database that can be accesed using:
```
http://localhost:8080/h2-console
```

The database is configured under
```
jdbc:h2:mem:ecommerce-test
```

## Configuration parameters

Job configuration like the total number of threads as well as other parameters can be configured in <b>application.yml</b> file 