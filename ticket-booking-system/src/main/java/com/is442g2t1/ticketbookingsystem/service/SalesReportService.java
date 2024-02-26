package com.is442g2t1.ticketbookingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is442g2t1.ticketbookingsystem.model.Event;
import com.is442g2t1.ticketbookingsystem.model.SalesReport;
import com.is442g2t1.ticketbookingsystem.repository.EventRepository;
import com.is442g2t1.ticketbookingsystem.repository.SalesReportRepository;

import java.util.Optional;

@Service
public class SalesReportService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SalesReportRepository salesReportRepository;

    // Generate a sales report for an event
    public SalesReport generateSalesReport(String eventId) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);

        if (!eventOpt.isPresent()) {
            // event does not exist
            throw new RuntimeException("Event not found with ID: " + eventId); 
        }

        Event event = eventOpt.get();
        SalesReport salesReport = new SalesReport();

        // Calculate tickets sold & total revenue
        int ticketsSold = event.getTotalNumTickets() - event.getNumTicketsAvailable();
        double totalRevenue = calculateTotalRevenue(event.viewEventPrice(), ticketsSold);
        
        // cancellation rate 
        double cancellationRate = calculateCancellationRate(event.getTotalNumTickets(), ticketsSold); 

        // Generate sales report
        salesReport.setEvent(event);
        salesReport.setTicketsSold(ticketsSold);
        salesReport.setTotalRevenue(totalRevenue);
        salesReport.setCancellationRate(cancellationRate);

        // Save  report
        return salesReportRepository.save(salesReport);
    }

    // Calculate total revenue
    private double calculateTotalRevenue(double ticketPrice, int ticketsSold) {
        return ticketPrice * ticketsSold;
    }

    // Calculate cancellation rate
    private double calculateCancellationRate(int totalTickets, int ticketsSold) {
        int ticketsCancelled = totalTickets - ticketsSold;
        return totalTickets > 0 ? (double) ticketsCancelled / totalTickets * 100 : 0;
    }
}
