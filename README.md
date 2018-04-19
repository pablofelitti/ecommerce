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

## How to see database
```
http://localhost:8080/h2-console
```

The database is configured under
```
jdbc:h2:mem:ecommerce-test
```

## Assumptions

1. Currency is always in Peso Argentino (ARS)