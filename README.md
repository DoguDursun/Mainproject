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




URLs


ProductController.java

GET /api/products          // Retrieve a list of all products

GET /api/products/{id}     // Retrieve a specific product by ID

POST /api/products         // Create a new product

PUT /api/products/{id}     // Update an existing product by ID

DELETE /api/products/{id}  // Delete a product by ID


UserController.java

GET /api/users               // Retrieve a list of all users

GET /api/users/{id}          // Retrieve a specific user by ID

POST /api/users              // Create a new user

PUT /api/users/{id}          // Update an existing user by ID

DELETE /api/users/{id}       // Delete a user by ID


RefundRequestController.java

GET /api/refunds                    // Retrieve a list of all refund requests

GET /api/refunds/{id}               // Retrieve a specific refund request by ID

POST /api/refunds                   // Create a new refund request

PUT /api/refunds/{id}               // Update an existing refund request by ID

DELETE /api/refunds/{id}            // Delete a refund request by ID

GET /api/refunds/tracking/{code}    // Retrieve refund status by tracking code


ShippingStatusController.java

GET /api/shipping-status                  // Retrieve a list of all shipping statuses

GET /api/shipping-status/{id}             // Retrieve a specific shipping status by ID

POST /api/shipping-status                 // Create a new shipping status

PUT /api/shipping-status/{id}             // Update an existing shipping status by ID

DELETE /api/shipping-status/{id}          // Delete a shipping status by ID

GET /api/shipping-status/tracking/{code}  // Retrieve shipping status by tracking code


ShoppingCartController.java

GET /api/cart/{userId}               // Retrieve the shopping cart for a specific user

POST /api/cart/{userId}/items        // Add an item to the shopping cart

PUT /api/cart/{userId}/items/{id}    // Update a specific item in the shopping cart

DELETE /api/cart/{userId}/items/{id} // Remove an item from the shopping cart

DELETE /api/cart/{userId}            // Clear the shopping cart


TransactionController.java

GET /api/transactions                 // Retrieve a list of all transactions

GET /api/transactions/{id}            // Retrieve a specific transaction by ID

POST /api/transactions                // Create a new transaction

PUT /api/transactions/{id}            // Update an existing transaction by ID

DELETE /api/transactions/{id}         // Delete a transaction by ID

GET /api/transactions/user/{userId}   // Retrieve all transactions for a specific user
