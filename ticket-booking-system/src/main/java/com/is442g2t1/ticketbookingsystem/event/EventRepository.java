package com.is442g2t1.ticketbookingsystem.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("SELECT e FROM Event e")
    List<Event> findAllEvents();
    
    @Query("SELECT e FROM Event e WHERE e.eventId = :eventId")
    Event searchById(@Param("eventId") Integer eventId);

    @Query("SELECT e FROM Event e WHERE e.eventTitle = ?1")
    Event searchByTitle(String eventTitle);

    @Query("SELECT e FROM Event e WHERE e.eventLoc = :eventLoc")
    Event searchByLocation(@Param("eventLoc") String location);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Event e WHERE e.eventId = :eventId")
    void deleteEventById(@Param("eventId") Integer eventId);
}
