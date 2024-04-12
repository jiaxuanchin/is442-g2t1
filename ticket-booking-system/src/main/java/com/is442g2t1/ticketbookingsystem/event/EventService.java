package com.is442g2t1.ticketbookingsystem.event;

import com.is442g2t1.ticketbookingsystem.event.dto.EventCreateDTO;
import com.is442g2t1.ticketbookingsystem.User.UserEntity;
import com.is442g2t1.ticketbookingsystem.User.UserRepository;
import com.is442g2t1.ticketbookingsystem.booking.Booking;
import com.is442g2t1.ticketbookingsystem.booking.BookingService;
import com.is442g2t1.ticketbookingsystem.ticket.Ticket;
import com.is442g2t1.ticketbookingsystem.ticket.TicketRepository;
import com.is442g2t1.ticketbookingsystem.email.EmailService;

import com.is442g2t1.response.StatusResponse;
import com.is442g2t1.response.SuccessResponse;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final BookingService bookingService;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Autowired
    public EventService(EventRepository eventRepository, EventMapper eventMapper, BookingService bookingService,
            UserRepository userRepository, EmailService emailService) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.bookingService = bookingService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public List<Event> getAllEvent() {
        return eventRepository.findAllEvents();
    }

    @Transactional
    public ResponseEntity<?> createEvent(EventCreateDTO eventCreateDTO) {
        try {
            System.out.println("Creating event with input data: " + eventCreateDTO);
            // Validation
            // empty fields
            if (eventCreateDTO.getEventTitle() == null || eventCreateDTO.getEventTitle().isBlank() ||
                    eventCreateDTO.getEventDate() == null || eventCreateDTO.getEventDate().toString().isBlank() ||
                    eventCreateDTO.getEventDesc() == null || eventCreateDTO.getEventDesc().isBlank() ||
                    eventCreateDTO.getEventLoc() == null || eventCreateDTO.getEventLoc().isBlank() ||
                    eventCreateDTO.getStartTime() == null || eventCreateDTO.getStartTime().isBlank() ||
                    eventCreateDTO.getEndTime() == null || eventCreateDTO.getEndTime().isBlank() ||
                    eventCreateDTO.getCapacity() == null || eventCreateDTO.getTicketPrice() == null) {
                StatusResponse statusResponse = new StatusResponse("Fields cannot be empty", HttpStatus.SC_BAD_REQUEST);
                return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(statusResponse);
            }

            // invalid capacity (capacity must be greater than 0)
            if (eventCreateDTO.getCapacity() <= 0) {
                StatusResponse invalidCapacityResponse = new StatusResponse("Capacity must be greater than 0",
                        HttpStatus.SC_BAD_REQUEST);
                return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(invalidCapacityResponse);
            }

            if (eventCreateDTO.getTicketPrice() <= 0) {
                StatusResponse invalidCapacityResponse = new StatusResponse("Ticket price must be greater than 0",
                        HttpStatus.SC_BAD_REQUEST);
                return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(invalidCapacityResponse);
            }

            // optional to set cancellation fee
            if (eventCreateDTO.getCancelFee() != null && eventCreateDTO.getCancelFee() <= 0) {
                StatusResponse invalidCapacityResponse = new StatusResponse("Cancel fee must be greater than 0",
                        HttpStatus.SC_BAD_REQUEST);
                return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(invalidCapacityResponse);
            }

            // Mappping dto to entity
            Event event = eventMapper.mapToEvent(eventCreateDTO);

            if (eventCreateDTO.getFilled() == null) {
                event.setFilled(0);
            }

            // cancel fee  -> must set to 0 if not will have error
            if (eventCreateDTO.getCancelFee() == null) {
                event.setCancelFee(0.00);
            }

            Event savedEvent = eventRepository.save(event);

            Integer eventId = savedEvent.getEventId();

            SuccessResponse successResponse = new SuccessResponse("Event created successfully", HttpStatus.SC_OK,
                    savedEvent);
            return ResponseEntity.ok().body(successResponse);

        } catch (Exception e) {

            e.printStackTrace();
            StatusResponse statusResponse = new StatusResponse("Error creating event : " + e.getMessage(),
                    HttpStatus.SC_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(statusResponse);

        }
    }

    public ResponseEntity<?> editEvent(EventCreateDTO eventCreateDTO, Integer eventId) {
        try {

            // Find existing event to update
            Optional<Event> optionalEvent = eventRepository.findById(eventId);

            // Event not found
            if (optionalEvent.isEmpty()) {
                StatusResponse notFoundResponse = new StatusResponse("Event not found", HttpStatus.SC_NOT_FOUND);
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(notFoundResponse);
            }

            Event existingEvent = optionalEvent.get();

            // Validation
            // empty fields
            if (eventCreateDTO.getEventTitle() == null || eventCreateDTO.getEventTitle().isBlank() ||
                    eventCreateDTO.getEventDate() == null || eventCreateDTO.getEventDate().toString().isBlank() ||
                    eventCreateDTO.getEventDesc() == null || eventCreateDTO.getEventDesc().isBlank() ||
                    eventCreateDTO.getEventLoc() == null || eventCreateDTO.getEventLoc().isBlank() ||
                    eventCreateDTO.getStartTime() == null || eventCreateDTO.getStartTime().isBlank() ||
                    eventCreateDTO.getEndTime() == null || eventCreateDTO.getEndTime().isBlank() ||
                    eventCreateDTO.getCapacity() == null || eventCreateDTO.getTicketPrice() == null) {
                StatusResponse statusResponse = new StatusResponse("Fields cannot be empty", HttpStatus.SC_BAD_REQUEST);
                return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(statusResponse);
            }

            // invalid capacity (capacity must be greater than 0)
            if (eventCreateDTO.getCapacity() <= 0) {
                StatusResponse invalidCapacityResponse = new StatusResponse("Capacity must be greater than 0",
                        HttpStatus.SC_BAD_REQUEST);
                return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(invalidCapacityResponse);
            }

            if (eventCreateDTO.getTicketPrice() <= 0) {
                StatusResponse invalidCapacityResponse = new StatusResponse("Ticket price must be greater than 0",
                        HttpStatus.SC_BAD_REQUEST);
                return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(invalidCapacityResponse);
            }

            // optional to set cancellation fee
            if (eventCreateDTO.getCancelFee() != null && eventCreateDTO.getCancelFee() <= 0) {
                StatusResponse invalidCapacityResponse = new StatusResponse("Cancel fee must be greater than 0",
                        HttpStatus.SC_BAD_REQUEST);
                return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(invalidCapacityResponse);
            }

            // Mappping dto to entity
            Event event = eventMapper.mapToEvent(eventCreateDTO);

            // eventId (take from existing event)
            event.setEventId(existingEvent.getEventId());

            if (eventCreateDTO.getFilled() == null) {
                // filled (take from existing event)
                event.setFilled(existingEvent.getFilled());
            }

            // cancel fee  -> must set to 0 if not will have error
            if (eventCreateDTO.getCancelFee() == null) {
                event.setCancelFee(0.00);
            }

            eventRepository.save(event);

            SuccessResponse successResponse = new SuccessResponse("Event edited successfully", HttpStatus.SC_OK, event);
            return ResponseEntity.ok().body(successResponse);

        } catch (Exception e) {

            e.printStackTrace();
            StatusResponse statusResponse = new StatusResponse("Error editing event : " + e.getMessage(),
                    HttpStatus.SC_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(statusResponse);

        }
    }

    public ResponseEntity<?> searchById(Integer eventId) {
        try {
            Event event = eventRepository.searchById(eventId);
            if (event != null) {
                SuccessResponse successResponse = new SuccessResponse("Success", HttpStatus.SC_OK, event);
                return ResponseEntity.ok().body(successResponse);
            } else {
                StatusResponse statusResponse = new StatusResponse("Event with id does not exist",
                        HttpStatus.SC_NOT_FOUND);
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(statusResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            StatusResponse statusResponse = new StatusResponse("Error getting by id" + e.getMessage(),
                    HttpStatus.SC_BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(statusResponse);
        }
    }

    public ResponseEntity<?> searchByTitle(String eventTitle) {
        try {
            Event event = eventRepository.searchByTitle(eventTitle);
            if (event != null) {
                SuccessResponse successResponse = new SuccessResponse("Success", HttpStatus.SC_OK, event);
                return ResponseEntity.ok().body(successResponse);
            } else {
                StatusResponse statusResponse = new StatusResponse("Event with title does not exist",
                        HttpStatus.SC_NOT_FOUND);
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(statusResponse);
            }
        } catch (Exception e) {
            StatusResponse statusResponse = new StatusResponse("Error getting event by title",
                    HttpStatus.SC_BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(statusResponse);
        }
    }

    public ResponseEntity<?> searchByLocation(String eventLoc) {
        try {
            List<Event> event = eventRepository.searchByLocation(eventLoc);
            if (!event.isEmpty()) {
                SuccessResponse successResponse = new SuccessResponse("Success", HttpStatus.SC_OK, event);
                return ResponseEntity.ok().body(successResponse);
            } else {
                StatusResponse statusResponse = new StatusResponse("Event with location does not exist",
                        HttpStatus.SC_NOT_FOUND);
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(statusResponse);
            }
        } catch (Exception e) {
            StatusResponse statusResponse = new StatusResponse("Error getting event by location",
                    HttpStatus.SC_BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(statusResponse);
        }
    }

    public ResponseEntity<?> cancelEvent(int eventId) {
        try {
            // Check if the event exists
            Optional<Event> optionalEvent = eventRepository.findById(eventId);
            if (optionalEvent.isEmpty()) {
                // Handle event not found
                StatusResponse notFoundResponse = new StatusResponse("Event not found", HttpStatus.SC_NOT_FOUND);
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(notFoundResponse);
            }

            // Cancel all bookings under the event
            ResponseEntity cancellationResponse = bookingService.cancelAllBookingsUnderEvent(eventId);

            // Delete the event from the database
            eventRepository.deleteById(eventId);

            SuccessResponse successResponse = new SuccessResponse("Event cancelled successfully", HttpStatus.SC_OK,
                    eventId);
            return ResponseEntity.ok().body(successResponse);

        } catch (Exception e) {
            e.printStackTrace();
            StatusResponse statusResponse = new StatusResponse("Error cancelling event: " + e.getMessage(),
                    HttpStatus.SC_INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(statusResponse);
        }
    }
}
