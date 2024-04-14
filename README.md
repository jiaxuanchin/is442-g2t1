# IS442-G2T1
# Ticketing Booking System

## Overview ##

The ticketing booking system is a platform that enables people to view and book tickets for events. It also enables event managers to create events, to ensure that people have the most up to date information. And allows ticketing officers to perform their duties such as performing on site ticket sales and verifying tickets.

## User Scenarios ##
* Allows customer to sign up
* Users can login via spring boot security.
* Customer makes a booking and conducts payment
* Customer cancels a booking and receives a refund
* Ticketing Officer verifies the validity of a ticket 
* Ticketing Officer makes a booking on behalf of a customer 
* Event Manager adds a ticketing officer
* Event Manager creates/edits an event 
* Event Manager generates sales statistics of an event

## Features ##
### Authentication ###
Users are able to sign up with a username and password or conveniently log in. Authentication and authorization are managed by Spring boot security.

### Event Listings ###
Users are able to browse through the vast selection of events uploaded by the event managers. To stay up to date with the latest events.

### Make a Booking ###
Customers can make a booking and conduct payment, where they are able to choose between paying from their e-wallet or via the credit card. The Stripe API enables us to facilitate the payment from their credit card. Ticketing officers can also make a booking on behalf of the customers, in such cases, payment by e-wallets is only accepted.

### Cancel Booking ###
Customers can cancel their bookings anytime before 24 hours of the event. Upon successful cancellation, any applicable cancellation fee and refunds will be credited to their balance.

### Cancel Event ###
Event Manager can cancel an event up to 24 hours before the event start time if they want to. Booking fees would be refunded to the corresponding customers.

### Notification through Email ###
Users are notified through emails about their event booking confirmation, event booking cancellation and event cancellation.

## Installation ##
1. Clone this repository to your local device and run main branch
### Frontend ###
1. Navigate to the folder '/my-ticket-booking-system' in your vsc terminal
2. Run the codes below to install the project dependencies and node modules (it will take around 5 minutes to finish running)
   ```
   npm install -g vite
   ```
   ```
   npm ci
   ```
3. To start up the application, run the command below
   ```
   npm run dev
   ```
### Backend ###
#### SQL Database ####
1. Start wamp server
2. Import `ticket-booking-system.sql`


#### For VSC ####
1. In another vsc terminal, navigate to the directory with `pom.xml` folder, in this case, should be at:
   ```
   /ticket-booking-system
   ```
2. Run command below
   ```
   mvn spring-boot:run
   ```

## Login Credentials ##
Customer:
1.
Email: euniceong.2021@scis.smu.edu.sg
Password: password


2.
Email: jolene.chew.2021@scis.smu.edu.sg
Password: password


Alternatively, you can also create a customer profile through the application's 'Sign up' function


Ticketing Officer:
1.
Email: kelly.goh.2021@scis.smu.edu.sg
Password: password


Event Manager:
1.
Email: jolene.chew.2021@scis.smu.edu.sg
Password: password


## Stripe Testing
For testing purposes, in the success case, input “4242 4242 4242 4242” for card number, and any number for expiry date and CVC number. 
For failure cases, input “4000 0000 0000 0002” for card number, and any number for expiry date and CVC number. For more test cases, you can refer to Stripe Documentation.