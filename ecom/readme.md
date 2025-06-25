## Prerequist:
* Java 21
* postgres.

## Configuration
* create a database named ecom
* configure password in ```genericEcom\ecom\src\main\resources\hibernate.cfg.xml```
* do clean install ``mvn clean install``
* run it

## Pending Work Items
* Adding new attributes in products
* Adding Table for customer(actual users)
* Adding Table for clients(clients of our business)
* UI for the endpoints (Learn REACT and do it)
* Payment integration

## works did so far
* implemented hibernate
* implemented spring security which validates user from database
* implemented password encoding with Bcrypt



branch feature/JPA --> migrated from hibernate to JPA.