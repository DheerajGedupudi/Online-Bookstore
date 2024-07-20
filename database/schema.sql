DROP DATABASE IF EXISTS bookstore_db;
CREATE DATABASE bookstore_db;
USE bookstore_db;

-- Drop tables with foreign key references first
DROP TABLE IF EXISTS Review;
DROP TABLE IF EXISTS Order_Item;
DROP TABLE IF EXISTS Book_Order;
DROP TABLE IF EXISTS Payment;
DROP TABLE IF EXISTS Cart_Item;
DROP TABLE IF EXISTS Cart;

-- Drop tables with no foreign key references last
DROP TABLE IF EXISTS Book;
DROP TABLE IF EXISTS Genre;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Admin;

-- Create the Admin table
CREATE TABLE Admin (
    admin_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
) CHARSET=utf8mb4;

-- Create the User table
CREATE TABLE User (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    is_blocked BOOLEAN NOT NULL DEFAULT FALSE
) CHARSET=utf8mb4;

-- Create the Genre table
CREATE TABLE Genre (
    genre_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
) CHARSET=utf8mb4;

-- Create the Book table
CREATE TABLE Book (
    book_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL,
    thumbnail_url VARCHAR(255),
    genre_id BIGINT,
    FOREIGN KEY (genre_id) REFERENCES Genre(genre_id)
) CHARSET=utf8mb4;

-- Create the Payment table
CREATE TABLE Payment (
    payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    payment_date DATETIME NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    mode_of_payment VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
) CHARSET=utf8mb4;

-- Create the BookOrder table
CREATE TABLE Book_Order (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    order_date DATETIME NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    current_status VARCHAR(50) NOT NULL,
    payment_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES User(user_id),
    FOREIGN KEY (payment_id) REFERENCES Payment(payment_id)
) CHARSET=utf8mb4;

-- Create the OrderItem table
CREATE TABLE Order_Item (
    order_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    book_id BIGINT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Book_Order(order_id),
    FOREIGN KEY (book_id) REFERENCES Book(book_id)
) CHARSET=utf8mb4;

-- Create the Cart table
CREATE TABLE Cart (
    cart_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
) CHARSET=utf8mb4;

-- Create the CartItem table
CREATE TABLE Cart_Item (
    cart_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT,
    book_id BIGINT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES Cart(cart_id),
    FOREIGN KEY (book_id) REFERENCES Book(book_id)
) CHARSET=utf8mb4;

-- Create the Review table
CREATE TABLE Review (
    review_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT,
    user_id BIGINT,
    rating INT NOT NULL,
    comment TEXT,
    review_date DATETIME NOT NULL,
    FOREIGN KEY (book_id) REFERENCES Book(book_id),
    FOREIGN KEY (user_id) REFERENCES User(user_id)
) CHARSET=utf8mb4;
