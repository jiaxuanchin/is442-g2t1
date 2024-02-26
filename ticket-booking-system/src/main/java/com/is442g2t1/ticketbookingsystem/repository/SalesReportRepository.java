package com.is442g2t1.ticketbookingsystem.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.is442g2t1.ticketbookingsystem.model.SalesReport;

@Repository
public interface SalesReportRepository extends JpaRepository<SalesReport, String> {
    
    ArrayList<SalesReport> getEventName(String eventName);
}
