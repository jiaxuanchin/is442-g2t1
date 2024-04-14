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
-- Database: `ticket-booking-system`
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
-- 5 events within 24 hours
(1, 'Musical', '2024-04-15', "Cats the Musical is coming to town! Cats is a sung-through musical with music by Andrew Lloyd Webber. It is based on the 1939 poetry collection Old Possum\'s Book of Practical Cats by T. S. Eliot.", 'The Capitol', '18:00', '21:00', 93, 100, '150.00', '20.00'),
(2, 'Movie', '2024-04-14', 'Avengers 100', 'Golden Village (Plaza Singapura)', '14:45', '18:00', 10, 2000, '12.00', '20.00'),
(3, 'Seminar', '2024-04-16', 'How to make use of ChatGPT', 'Singapore Management University', '09:00', '13:00', 13, 40, '500.00', '20.00'),
(4, 'Concert', '2024-04-15', 'Disney is coming to town! Meet Mickey, Minney, Else, Anna, Cinderella and more', 'National Stadium', '19:30', '23:00', 10, 200, '150.00', '20.00'),
(5, 'Play', '2024-04-15', 'Romeo & Juliet is a tragedy written by William Shakespeare early in his career about the romance between two Italian youths from feuding families.', 'Esplanade', '19:00', '22:00', 499, 500, '78.00', '20.00'),
-- 5 events after 6 months
(6, 'Music Festival', '2025-04-14', 'Annual music festival featuring top artists', 'Marina Bay Sands', '09:00', '23:00', 5, 5000, '200.00', '30.00'),
(7, 'Tech Conference', '2026-10-05', 'Explore the latest trends in technology', 'Suntec Convention Centre', '09:00', '17:00', 3, 1000, '300.00', '50.00'),
(8, 'Food Expo', '2026-05-12', 'Sample cuisines from around the world', 'Singapore Expo', '11:00', '20:00', 7, 150, '50.00', '10.00'),
(9, 'Fashion Show', '2026-08-28', 'Showcasing the latest fashion trends', 'Resorts World Sentosa', '19:00', '22:00', 3, 2000, '100.00', '20.00'),
(10, 'Sports Tournament', '2026-09-10', 'National sports tournament', 'Singapore Sports Hub', '10:00', '18:00', 9, 3000, '75.00', '15.00'),
-- 5 events within in the past
(11, 'Art Exhibition', '2023-03-15', 'Explore contemporary art pieces', 'ArtScience Museum', '10:00', '19:00', 5, 1500, '20.00', '5.00'),
(12, 'Comedy Show', '2023-06-02', 'An evening of laughter with top comedians', 'Capitol Theatre', '20:00', '22:30', 6, 1000, '40.00', '10.00'),
(13, 'Fitness Expo', '2023-02-08', 'Discover the latest in fitness and wellness', 'Singapore Expo', '09:00', '18:00', 7, 5000, '30.00', '5.00'),
(14, 'Technology Summit', '2023-09-20', 'Bringing together tech leaders and innovators', 'Marina Bay Sands', '08:30', '17:30', 7, 2000, '250.00', '50.00'),
(15, 'Culinary Workshop', '2023-11-12', 'Learn to cook gourmet dishes from renowned chefs like Gordon Ramsay and more!', 'Culinary Academy', '14:00', '17:00', 4, 50, '150.00', '30.00');


INSERT INTO Booking (user_id, event_id, number_of_tickets, booking_timestamp) VALUES
-- Bookings within 24 hours
(1, 3, 3, TIMESTAMP('2024-04-12 14:45:00')),
-- Bookings for events that have past
(1, 11, 3, TIMESTAMP('2023-10-20 17:20:00')),
(1, 12, 2, TIMESTAMP('2023-10-03 12:35:00')),
(1, 13, 1, TIMESTAMP('2023-01-08 09:45:00'));
-- Bokings within 6 months
(1, 4, 2, TIMESTAMP('2024-02-15 19:30:00'));
(1, 3, 2, TIMESTAMP('2024-01-16 09:00:00'));


  -- ticket_id INT(8) ZEROFILL NOT NULL PRIMARY KEY AUTO_INCREMENT,
  -- booking_id INT NOT NULL,
  -- attendance BOOLEAN DEFAULT FALSE

INSERT INTO Ticket VALUES
(00000001, 1, false),
(00000002, 1, false),
(00000003, 1, false),
(00000004, 2, false),
(00000005, 2, false),
(00000006, 3, false),
(00000007, 3, false),
(00000008, 4, false),
(00000009, 4, false),
(00000010, 4, false),
(00000011, 5, false),
(00000012, 6, false),

INSERT INTO Token VALUES
(1, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJldW5pY2VvbmcuMjAyMUBzY2lzLnNtdS5lZHUuc2ciLCJ1c2VyX2lkIjoxLCJyb2xlX2lkIjp7ImlkIjoxLCJuYW1lIjoiY3VzdG9tZXIifSwiaWF0IjoxNzExNTEzMjE1LCJleHAiOjE3MTE1OTk2MTV9.zh6RdIoKNMzJa7_yZ-vZHllJI7gAceMpwr1sLacHNB4', true, true, 1),
(2, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJldW5pY2VvbmcuMjAyMUBzY2lzLnNtdS5lZHUuc2ciLCJ1c2VyX2lkIjoxLCJyb2xlX2lkIjp7ImlkIjoxLCJuYW1lIjoiY3VzdG9tZXIifSwiaWF0IjoxNzExNTIxNjY3LCJleHAiOjE3MTE2MDgwNjd9.QnZTq9R4_4bypTP0G2_LlK_GIafSMjcqEL0Q-DCC2Fw', false, false, 1),
(3, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqeGNoaW4uMjAyMUBzY2lzLnNtdS5lZHUuc2ciLCJ1c2VyX2lkIjo0LCJyb2xlX2lkIjp7ImlkIjoxLCJuYW1lIjoiY3VzdG9tZXIifSwiaWF0IjoxNzExMjkxNTg2LCJleHAiOjE3MTEzNzc5ODZ9.iKZjd2U59B-ZFG9KTl4c29m-wTUAtG7wjVl738Kh0NU', false, false, 3);
