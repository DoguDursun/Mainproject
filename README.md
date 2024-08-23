Project Description
This project is an e-commerce application developed using Spring Boot. 
It handles various e-commerce processes such as users, products, shopping carts, transactions, refund requests, and shipping statuses. 
The application integrates with a PostgreSQL database and uses Spring Data JPA for data management. 
Additionally, scheduled tasks are implemented using Spring Quartz to automate certain processes.

Technologies Used
Spring Boot: 3.3.2
Spring Data JPA: ORM framework for database interaction.
Spring Quartz: For scheduling and running cron jobs.
PostgreSQL: Relational database used for data storage.
Lombok: To reduce boilerplate code for model classes.
Modules in the Project
User Management:

Controller: UserController.java
Description: Manages user operations (registration, updating, deletion).
Product Management:

Controller: ProductController.java
Description: Manages product operations (adding, updating, listing products).
Shopping Cart Management:

Controller: ShoppingCartController.java
Description: Manages shopping cart operations (adding products, removing products, viewing the cart).
Transaction Management:

Controller: TransactionController.java
Description: Manages purchase transactions and their records.
Refund Request Management:

Controller: RefundRequestController.java
Description: Manages refund requests and tracks the related processes.
Shipping Status Management:

Controller: ShippingStatusController.java
Description: Tracks and manages shipping statuses.
Using Docker
This application can be run on Docker. You can pull the application from Docker Hub and run it.

Pull and Run from Docker Hub:
https://hub.docker.com/repository/docker/dogudursun/mainproject/general
docker pull dogudursun/mainproject:latest
The application will be running on http://localhost:8080. The database will be running on PostgreSQL at port localhost:5432.

Cron Jobs

This project includes several cron jobs to automate specific tasks:

TestUser Data Insertion: This cron job adds data to the TestUser table every 2 minutes. The primary purpose of this cron job is to allow users to verify whether the project is running correctly in their environment by checking the logs.

Refund Request Processing: This cron job is responsible for periodically processing refund requests. It ensures that refund requests are handled according to the predefined business logic, updating their status as necessary.

Shipping Status Update: This cron job updates the shipping status in the database at regular intervals. It ensures that the latest shipping information is reflected in the system, which is crucial for tracking and managing orders.

These cron jobs are implemented using Spring Quartz and are essential for automating and managing background tasks within the application.
