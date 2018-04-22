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

## TODO

The cart job process uses Spring Batch. As optimistic lock has been implemented for product stock, so, whenever 
two or more threads try to update the same product stock, only one is able to do so. The thread that could not 
update the stock will throw OptimisticLockingFailureException which is correct, but the problem I am facing is 
that the thread stops processing further carts. This issue does not make stock data be corrupt, it just finishes 
early the thread that throws that exception. Next time the job runs will resume processing remaining carts. I 
would need more time to understand how Spring Batch works in order to fix this issue. 