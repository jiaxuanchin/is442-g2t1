-- phpMyAdmin SQL Dump
-- version 4.9.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Sep 22, 2021 at 10:24 AM
-- Server version: 5.7.26
-- PHP Version: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+08:00";
SET FOREIGN_KEY_CHECKS= 0;

--
-- Database: `heartchat_db`
--

CREATE DATABASE IF NOT EXISTS `ticket-booking-system` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `ticket-booking-system`;

-- --------------------------------------------------------

--
-- Table structure for table `Roles`
--

DROP TABLE IF EXISTS Role;

CREATE TABLE IF NOT EXISTS Role (
  role_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  role_name VARCHAR(50) NOT NULL
);

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS Users;

CREATE TABLE IF NOT EXISTS Users (
  user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  role_id INT NOT NULL,
  user_fname VARCHAR(50) NOT NULL,
  user_lname VARCHAR(50) NOT NULL,
  email VARCHAR(255) NOT NULL,
--   salt VARCHAR(50) NOT NULL,
 `password` VARCHAR(255) NOT NULL,
  user_type VARCHAR(50) NOT NULL,
  balance DOUBLE PRECISION DEFAULT NULL,
  FOREIGN KEY (role_id) REFERENCES Role(role_id)
);

-- --------------------------------------------------------


--
-- Table structure for table `Event`
--

DROP TABLE IF EXISTS Event;

CREATE TABLE IF NOT EXISTS Event (
  event_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  event_title VARCHAR(255) NOT NULL,
  event_date DATE NOT NULL,
  event_description TEXT,
  event_location VARCHAR(255) NOT NULL,
  event_start_time VARCHAR(255) NOT NULL,
  event_end_time VARCHAR(255),
  filled INT NOT NULL DEFAULT 0,
  capacity INT NOT NULL,
  ticket_price Decimal(10,2) NOT NULL,
  cancel_fee Decimal(10,2) NOT NULL DEFAULT 0.00
);

-- --------------------------------------------------------

--
-- Table structure for table `Booking`
--

DROP TABLE IF EXISTS Booking;

CREATE TABLE IF NOT EXISTS Booking (
  booking_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  event_id INT NOT NULL,
  number_of_tickets INT,
  booking_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- --------------------------------------------------------

--
-- Table structure for table `Ticket`
--

DROP TABLE IF EXISTS Ticket;

CREATE TABLE IF NOT EXISTS Ticket (
  ticket_id INT(8) ZEROFILL NOT NULL PRIMARY KEY AUTO_INCREMENT,
  booking_id INT NOT NULL,
  attendance BOOLEAN DEFAULT FALSE
);

-- --------------------------------------------------------

--
-- Table structure for table `Token`
--

DROP TABLE IF EXISTS Token;

CREATE TABLE IF NOT EXISTS Token (
  token_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  token VARCHAR(255) NOT NULL,
  revoked BOOLEAN DEFAULT FALSE,
  expired BOOLEAN DEFAULT FALSE,
  user_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- --------------------------------------------------------

-- --------------------------------------------------------
-- Constraints for table `User_Preference`
ALTER TABLE `Booking`
  ADD CONSTRAINT `Booking_fk` FOREIGN KEY (user_id) REFERENCES Users(user_id),
  ADD CONSTRAINT `Booking_fk2` FOREIGN KEY (event_id) REFERENCES Event(event_id);

-- --------------------------------------------------------
-- Constraints for table `Event_Category`
ALTER TABLE `Ticket`
  ADD CONSTRAINT `Ticket_fk` FOREIGN KEY (booking_id) REFERENCES Booking(booking_id);

-- --------------------------------------------------------


SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO Role (role_id, role_name) VALUES
(1,"customer"),
(2,"event_manager"),
(3,"ticketing_officer");

INSERT INTO Users (user_id, role_id, user_fname,user_lname,email, `password`, user_type, balance) VALUES
(1, 1, 'Eunice','Ong','euniceong.2021@scis.smu.edu.sg', '$2a$10$9iPJ9/xQEIJlHzFHcvUEX.3Z8Trj9G/.M4.AKdjsr1zW83iFgpThC', 'customer', 1000),
(2, 2, 'Jolene','Chew','jolene.chew.2021@scis.smu.edu.sg', '$2a$10$SQ67EdPDW/yWSbFr065A1O131e1T0iKm6plIclQ8Xf6DhZjCCV80S', 'event_manager', NULL),
(3, 3, 'Kelly','Goh','kelly.goh.2021@scis.smu.edu.sg', '$2a$10$5SAphSHTMrFBiO1sXr3Y3u3ybrqHXDopn2g1jl8u2X.unz2vUkfwu', 'ticketing_officer', NULL);


INSERT INTO Event (event_id, event_title, event_date, event_description, event_location, event_start_time, event_end_time, filled, capacity, ticket_price, cancel_fee) VALUES
(1, 'Musical', '2023-09-15', 'Disney is coming to town', 'The Capitol', '18:00', '21:00', 0, 2000, 150.00, 20.00),
(2, 'Movie', '2024-06-22', 'Avengers 100', 'Golden Village (Plaza Singapura)', '14:45', '18:00', 0, 2000, 12.00, 20.00),
(3, 'Seminar', '2023-12-15', 'How to make use of ChatGPT', 'Singapore Management University', '09:00', '13:00', 0, 40, 500.00, 20.00),
(4, 'Concert', '2024-03-02', 'Disney is coming to town', 'National Stadium', '19:30', '23:00', 0, 25000, 150.00, 20.00),
(5, 'Play', '2023-11-29', 'Romeo & Juliet', 'Esplanade', '19:00', '22:00', 0, 3000, 78.00, 20.00),
-- 10 events happening in 2026
(6, 'Music Festival', '2026-07-18', 'Annual music festival featuring top artists', 'Marina Bay Sands', '15:00', '23:00', 0, 5000, 200.00, 30.00),
(7, 'Tech Conference', '2026-10-05', 'Explore the latest trends in technology', 'Suntec Convention Centre', '09:00', '17:00', 0, 1000, 300.00, 50.00),
(8, 'Food Expo', '2026-05-12', 'Sample cuisines from around the world', 'Singapore Expo', '11:00', '20:00', 0, 8000, 50.00, 10.00),
(9, 'Fashion Show', '2026-08-28', 'Showcasing the latest fashion trends', 'Resorts World Sentosa', '19:00', '22:00', 0, 2000, 100.00, 20.00),
(10, 'Sports Tournament', '2026-09-10', 'National sports tournament', 'Singapore Sports Hub', '10:00', '18:00', 0, 3000, 75.00, 15.00),
-- 10 past events
(11, 'Art Exhibition', '2023-03-15', 'Explore contemporary art pieces', 'ArtScience Museum', '10:00', '19:00', 0, 1500, 20.00, 5.00),
(12, 'Comedy Show', '2023-06-02', 'An evening of laughter with top comedians', 'Capitol Theatre', '20:00', '22:30', 0, 1000, 40.00, 10.00),
(13, 'Fitness Expo', '2024-02-08', 'Discover the latest in fitness and wellness', 'Singapore Expo', '09:00', '18:00', 0, 5000, 30.00, 5.00),
(14, 'Technology Summit', '2025-09-20', 'Bringing together tech leaders and innovators', 'Marina Bay Sands', '08:30', '17:30', 0, 2000, 250.00, 50.00),
(15, 'Culinary Workshop', '2024-11-12', 'Learn to cook gourmet dishes from renowned chefs', 'Culinary Academy', '14:00', '17:00', 0, 50, 150.00, 30.00),
-- 20 events happening in the near 6 months from now
(16, 'Health Conference', '2024-6-25', 'Exploring latest advancements in healthcare', 'Suntec Convention Centre', '09:00', '18:00', 0, 1500, 200.00, 40.00),
(17, 'Music Concert', '2024-6-05', 'Live performances by popular bands', 'The Star Performing Arts Centre', '19:30', '23:00', 0, 3000, 80.00, 15.00),
(18, 'Startup Summit', '2024-6-10', 'Showcasing innovative startups and entrepreneurs', 'Marina Bay Sands', '10:00', '18:00', 0, 2000, 150.00, 30.00),
(19, 'Art Workshop', '2024-01-15', 'Hands-on art workshop for all skill levels', 'National Gallery Singapore', '11:00', '14:00', 0, 100, 50.00, 10.00),
(20, 'Fashion Exhibition', '2024-02-20', 'Featuring designers from around the globe', 'Resorts World Sentosa', '12:00', '20:00', 0, 500, 75.00, 15.00),
(21, 'Film Festival', '2024-03-12', 'Celebrating international cinema', 'Golden Village (VivoCity)', '18:00', '23:00', 0, 1000, 30.00, 5.00),
(22, 'Science Symposium', '2024-04-18', 'Exploring breakthroughs in science and technology', 'Science Centre Singapore', '09:30', '17:30', 0, 800, 100.00, 20.00),
(23, 'Literary Festival', '2024-05-22', 'Celebrating literature and authors', 'Singapore Writers Festival', '10:00', '22:00', 0, 200, 25.00, 5.00),
(24, 'Dance Performance', '2024-06-30', 'An evening of mesmerizing dance performances', 'Esplanade - Theatres on the Bay', '19:00', '21:30', 0, 400, 60.00, 10.00),
(25, 'Gastronomy Tour', '2024-07-05', 'A culinary journey through the city', 'Various Locations', '11:00', '17:00', 0, 50, 120.00, 20.00);


INSERT INTO Booking (user_id, event_id, number_of_tickets, booking_timestamp) VALUES
-- Bookings for events happening in 2026
(1, 6, 3, TIMESTAMP('2026-07-16 14:30:00')),
(2, 6, 2, TIMESTAMP('2026-07-17 11:45:00')),
(3, 7, 1, TIMESTAMP('2026-10-04 09:15:00')),
-- Bookings for past events
(1, 11, 2, TIMESTAMP('2023-03-13 08:10:00')),
(2, 12, 3, TIMESTAMP('2023-06-01 18:30:00')),
(3, 13, 1, TIMESTAMP('2024-02-07 10:55:00')),
-- Bookings for events happening in the near 6 months from now
(1, 16, 3, TIMESTAMP('2024-10-23 17:20:00')),
(2, 17, 2, TIMESTAMP('2024-11-03 12:35:00')),
(3, 18, 1, TIMESTAMP('2024-12-08 09:45:00')),
-- Additional bookings
(1, 19, 5, TIMESTAMP('2024-12-15 14:00:00')),
(2, 20, 4, TIMESTAMP('2024-12-22 10:30:00')),
(3, 21, 3, TIMESTAMP('2025-01-05 16:45:00')),
(1, 22, 2, TIMESTAMP('2025-01-10 11:20:00')),
(2, 23, 1, TIMESTAMP('2025-02-20 09:00:00')),
(3, 24, 5, TIMESTAMP('2025-03-03 15:10:00')),
(1, 25, 4, TIMESTAMP('2025-03-15 13:45:00')),
(2, 22, 3, TIMESTAMP('2025-04-02 18:20:00')),
(3, 23, 2, TIMESTAMP('2025-04-12 10:55:00')),
(1, 24, 1, TIMESTAMP('2025-05-20 11:30:00')),
(2, 22, 5, TIMESTAMP('2025-06-08 14:15:00')),
(3, 2, 4, TIMESTAMP('2025-06-20 16:40:00'));

INSERT INTO Ticket VALUES
(00000001, 1, false),
(00000002, 1, false),
(00000003, 2, false),
(00000004, 3, false),
(00000005, 3, false),
(00000006, 3, false),
(00000007, 4, false),
(00000008, 4, false),
(00000009, 4, false),
(00000010, 4, false),
(00000011, 5, false),
(00000012, 5, false);

INSERT INTO Token VALUES
(1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJldW5pY2VvbmcuMjAyMUBzY2lzLnNtdS5lZHUuc2ciLCJ1c2VyX2lkIjoxLCJyb2xlX2lkIjp7ImlkIjoxLCJuYW1lIjoiY3VzdG9tZXIifSwiaWF0IjoxNzExNTEzMjE1LCJleHAiOjE3MTE1OTk2MTV9.zh6RdIoKNMzJa7_yZ-vZHllJI7gAceMpwr1sLacHNB4', true, true, 1),
(2, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJldW5pY2VvbmcuMjAyMUBzY2lzLnNtdS5lZHUuc2ciLCJ1c2VyX2lkIjoxLCJyb2xlX2lkIjp7ImlkIjoxLCJuYW1lIjoiY3VzdG9tZXIifSwiaWF0IjoxNzExNTIxNjY3LCJleHAiOjE3MTE2MDgwNjd9.QnZTq9R4_4bypTP0G2_LlK_GIafSMjcqEL0Q-DCC2Fw', false, false, 1),
(3, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqeGNoaW4uMjAyMUBzY2lzLnNtdS5lZHUuc2ciLCJ1c2VyX2lkIjo0LCJyb2xlX2lkIjp7ImlkIjoxLCJuYW1lIjoiY3VzdG9tZXIifSwiaWF0IjoxNzExMjkxNTg2LCJleHAiOjE3MTEzNzc5ODZ9.iKZjd2U59B-ZFG9KTl4c29m-wTUAtG7wjVl738Kh0NU', false, false, 3);

INSERT INTO Ticket (booking_id) VALUES
-- Tickets for bookings for events happening in 2026
(1),
(1),
(2),
(3),
(3),
(3),
(4),
(4),
-- Tickets for bookings for past events
(5),
(5),
(5),
(6),
(7),
(7),
(7),
(8),
(8),
(9),
(10),
(10),
(10),
(10),
(10),
(11),
(11),
(11),
(11),
(12),
(12),
(12),
(13),
(13),
(14),
(15),
(15),
(15),
(15),
(15),
(16),
(16),
(16),
(16),
(17),
(17),
(17),
(18),
(18),
(19),
(20),
(20),
(20),
(20),
(20),
(21),
(21),
(21),
(21);
