package com.is442g2t1.ticketbookingsystem.model;

@Entity
@Table(name = "ticketing_officers")

public class TicketingOfficer extends User {

    

    @OneToMany(mappedBy = "ticketingOfficer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TicketSale> ticketSales = new HashSet<>();


    public TicketingOfficer(String email, String password) {
        super(email, password);
    }

    
    public boolean validateTicket(Ticket ticket) {
        // Placeholder for ticket validation logic
        return true;
    }

    public void sellTicket(Event event, int numTickets) {
        // Placeholder for the logic to sell tickets
        
    }

    // Getters and Setters
    public Set<TicketSale> getTicketSales() {
        return ticketSales;
    }

    public void setTicketSales(Set<TicketSale> ticketSales) {
        this.ticketSales = ticketSales;
    }

   
}
