# Online Bookstore

This project is a comprehensive online bookstore application developed as part of the CSEN 280 course (Database Systems) at Santa Clara University. The main objective is to demonstrate the use of a relational database to perform all kinds of CRUD (Create, Read, Update, Delete) operations on multiple entities within a real-world context.

## Project Overview

The Online Bookstore allows users to browse, search, and purchase books while providing admins the functionality to manage inventory, users, and orders. This project showcases a relational database integrated with a Spring Boot application, emphasizing practical CRUD operations and data management.

## Project Structure

The project is divided into two main applications:
1. **Admin Application**: For managing books, genres, users, and orders.
2. **User Application**: For browsing books, managing the cart, placing orders, and writing reviews.

## Technologies Used

- **Backend**: Spring Boot, Spring Security, Hibernate
- **Frontend**: Thymeleaf, HTML, CSS
- **Database**: MySQL
- **Build Tool**: Maven

## Database Schema

The database schema includes the following tables: Admin, User, Genre, Book, Payment, BookOrder, OrderItem, Cart, CartItem, and Review. Each table is designed to support the application’s functionality with appropriate relationships and constraints.

For detailed table creation SQL scripts, refer to the [schema.sql](database/schema.sql) file.

## Features

### Admin Functionality:
- **CRUD Operations**: Manage books and genres.
- **Stock Management**: Update book quantities and inventory.
- **User Management**: Block or unblock users.
- **Order Management**: View and cancel user orders.
- **Review Management**: View reviews for each book.

### User Functionality:
- **Book Browsing**: Search, filter, and sort books.
- **Cart Management**: Add books to cart, edit cart items, and delete items.
- **Order Processing**: Checkout cart items, select payment method, and view order history.
- **Review System**: Write reviews and rate purchased books.

### User Authentication and Profile Management:
- Secure sign-up, log-in, and profile management.
- Role-based access control for admins and users.

## Installation

### Prerequisites
- JDK 8 or higher
- Maven
- MySQL

### Steps

1. Clone the repository:
    ```sh
    git clone https://github.com/DheerajGedupudi/Online-Bookstore.git
    ```

2. Set up the database:
    - Ensure MySQL is running.
    - Create a new database called `bookstore_db`.
    - Run the SQL script to create the necessary tables:
        ```sh
        mysql -u your_username -p bookstore_db < database/schema.sql
        ```

3. Build and run the applications:

    Navigate to the `adminapplication` and `userapplication` directories and run the following commands for each:
    ```sh
    mvn clean install
    mvn spring-boot:run
    ```

4. Access the applications:
    - Admin Application: `http://localhost:8080/admin`
    - User Application: `http://localhost:8081/user`

**Author**: Dheeraj Sai Venkat Gedupudi

---

**Note**: This project was developed as a graded assignment for the CSEN 280 course (Database Systems) at Santa Clara University.
