-- DatabaseSchema.sql
CREATE DATABASE IF NOT EXISTS hotelDB;

USE hotelDB;

CREATE TABLE IF NOT EXISTS Hotels (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Rooms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    number INT NOT NULL,
    type ENUM('SINGLE', 'DOUBLE', 'SUITE') NOT NULL,
    available BOOLEAN NOT NULL,
    hotel_id INT,
    FOREIGN KEY (hotel_id) REFERENCES Hotels(id)
);

CREATE TABLE IF NOT EXISTS Clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Reservations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_id INT,
    room_id INT,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    FOREIGN KEY (client_id) REFERENCES Clients(id),
    FOREIGN KEY (room_id) REFERENCES Rooms(id)
);
