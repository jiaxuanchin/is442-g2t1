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
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS Users;

CREATE TABLE IF NOT EXISTS Users (
  user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  role_name VARCHAR(50) NOT NULL,
  user_fname VARCHAR(50) NOT NULL,
  user_lname VARCHAR(50) NOT NULL,
  email VARCHAR(255) NOT NULL,
--   salt VARCHAR(50) NOT NULL,
  password VARCHAR(255) NOT NULL
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
  booking_timestamp TIMESTAMP NOT NULL
);

-- --------------------------------------------------------\

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

INSERT INTO Users (role_name,user_fname,user_lname,email,password) VALUES
('Customer','Eunice','Ong','euniceong.2021@scis.smu.edu.sg', 'password'),
('Event Manager','Jolene','Chew','jolene.chew.2021@scis.smu.edu.sg', 'password'),
('Ticketing Officer','Kelly','Goh','kelly.goh.2021@scis.smu.edu.sg', 'password');

INSERT INTO Event (event_id, event_title, event_date, event_description, event_location, event_start_time, event_end_time, filled, capacity, ticket_price, cancel_fee) VALUES
(1, 'Musical', '2023-09-15', 'Disney is coming to town', 'The Capitol', '18:00', '21:00', 0, 2000, 220.00, 20.00),
(2, 'Movie', '2024-01-22', 'Avengers 100', 'Golden Village (Plaza Singapura)', '14:45', '18:00', 0, 2000, 220.00, 20.00),
(3, 'Seminar', '2023-12-15', 'How to make use of ChatGPT', 'Singapore Management University', '09:00', '13:00', 0, 40, 220.00, 20.00),
(4, 'Concert', '2024-03-02', 'Disney is coming to town', 'National Stadium', '19:30', '23:00', 0, 25000, 220.00, 20.00),
(5, 'Play', '2023-11-29', 'Romeo & Juliet', 'Esplanade', '19:00', '22:00', 0, 3000, 220.00, 20.00);

INSERT INTO Booking VALUES
(1,1,4,2, TIMESTAMP('2023-09-13 10:04:30')),
(2,1,3,1, TIMESTAMP('2024-01-02 11:27:33')),
(3,1,2,3, TIMESTAMP('2023-10-13 14:09:56')),
(4,1,1,4, TIMESTAMP('2024-02-01 18:43:02')),
(5,1,5,2, TIMESTAMP('2023-09-30 23:59:04'));


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