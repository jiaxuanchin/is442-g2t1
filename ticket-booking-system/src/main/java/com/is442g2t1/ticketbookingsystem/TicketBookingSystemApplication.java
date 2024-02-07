package com.is442g2t1.ticketbookingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class TicketBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketBookingSystemApplication.class, args);
	}

}
