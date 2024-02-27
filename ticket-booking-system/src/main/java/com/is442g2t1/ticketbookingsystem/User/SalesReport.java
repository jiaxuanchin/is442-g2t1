package com.is442g2t1.ticketbookingsystem.User;

public class SalesReport {

    private Event event; // Event object
    private int ticketsSold;
    private double totalRevenue;
    private double cancellationRate;

    //Default Constructor
    public SalesReport() {
        
    }

    // Full constructor
    public SalesReport(Event event, int ticketsSold, double totalRevenue, double cancellationRate) {
        this.event = event;
        this.ticketsSold = ticketsSold;
        this.totalRevenue = totalRevenue;
        this.cancellationRate = cancellationRate;
    }

    // Getters and Setters
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getCancellationRate() {
        return cancellationRate;
    }

    public void setCancellationRate(double cancellationRate) {
        this.cancellationRate = cancellationRate;
    }

    // Calculating remaining tickets
    public int getRemainingTickets() {
        return event.getTotalNumTickets() - getTicketsSold();
    }
}
